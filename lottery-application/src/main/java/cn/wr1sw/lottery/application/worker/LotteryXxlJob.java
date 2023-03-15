package cn.wr1sw.lottery.application.worker;

import cn.bugstack.middleware.db.router.strategy.IDBRouterStrategy;
import cn.wr1sw.lottery.application.mq.producer.KafkaProducer;
import cn.wr1sw.lottery.common.Constants;
import cn.wr1sw.lottery.common.Result;
import cn.wr1sw.lottery.domain.activity.model.vo.ActivityVO;
import cn.wr1sw.lottery.domain.activity.model.vo.InvoiceVO;
import cn.wr1sw.lottery.domain.activity.service.deploy.IActivityDeploy;
import cn.wr1sw.lottery.domain.activity.service.partake.IActivityPartake;
import cn.wr1sw.lottery.domain.activity.service.stateflow.IStateHandler;
import com.alibaba.fastjson.JSON;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author wr1sw
 * @version 1.0.0
 * @description
 */
@Component
public class LotteryXxlJob {

    private Logger logger = LoggerFactory.getLogger(LotteryXxlJob.class);

    @Resource
    private IActivityDeploy activityDeploy;

    @Resource
    private IActivityPartake activityPartake;

    @Resource
    private IStateHandler stateHandler;

    @Resource
    private IDBRouterStrategy dbRouter;


    @Resource
    private KafkaProducer kafkaProducer;


    @XxlJob("lotteryActivityStateJobHandler")
    public void lotteryActivityStateJobHandler() {

        logger.info("扫描活动状态 Begin");

        List<ActivityVO> activityVOList = activityDeploy.scanToDoActivityList(0L);
        if (activityVOList.isEmpty()) {
            logger.info("扫描活动状态 End 暂无符合需要扫描的活动列表");
            return;
        }

        while (!activityVOList.isEmpty()) {
            for (ActivityVO activityVO : activityVOList) {
                Integer state = activityVO.getState();
                switch (state) {
                    case 4:
                        Result state4Result = stateHandler.doing(activityVO.getActivityId(), Constants.ActivityState.PASS);
                        logger.info("扫描活动状态为活动中 结果：{} activityId：{} activityName：{} creator：{}",
                                JSON.toJSONString(state4Result), activityVO.getActivityId(), activityVO.getActivityName(), activityVO.getCreator());
                        break;
                    case 5:
                        if (activityVO.getEndDateTime().before((new Date()))) {
                            Result state5Result  = stateHandler.close(activityVO.getActivityId(), Constants.ActivityState.DOING);
                            logger.info("扫描活动状态为关闭 结果：{} activityId：{} activityName：{} creator：{}",
                                    JSON.toJSONString(state5Result), activityVO.getActivityId(), activityVO.getActivityName(), activityVO.getCreator());
                        }
                        break;

                    default: break;
                }
            }

            // 获取集合中最后一条记录，继续扫描后面10条记录
            ActivityVO activityVO = activityVOList.get(activityVOList.size() - 1);
            activityVOList = activityDeploy.scanToDoActivityList(activityVO.getId());

        }

        logger.info("扫描活动状态 End");
    }

    @XxlJob("lotteryOrderMQStateJobHandler")
    public void lotteryOrderMQStateJobHandler() {
        // 验证参数
        String jobParam = XxlJobHelper.getJobParam();
        if (jobParam == null) {
            logger.info("扫描用户抽奖奖品发放MQ状态[Table = 2*4] 错误 params is null");
            return;
        }

        // 获取分布式任务配置参数信息 参数配置格式：1,2,3 也可以是指定扫描一个，也可以配置多个库，按照部署的任务集群进行数量配置，均摊分别扫描效率更高
        String[] params = jobParam.split(",");
        logger.info("扫描用户抽奖奖品发放MQ状态[Table = 2*4] 开始 params：{}", JSON.toJSONString(params));

        if (params.length == 0) {
            logger.info("扫描用户抽奖奖品发放MQ状态[Table = 2*4] 结束 params is null");
            return;
        }

        // 获取分库分表配置下的分表数
        int tbCount = dbRouter.tbCount();

        // 循环遍历指定扫描库
        for (String param: params) {
            // 获取当前任务扫描的指定分库
            int dbCount = Integer.parseInt(param);

            // 判断配置指定扫描库数，是否存在
            if (dbCount > dbRouter.dbCount()) {
                logger.info("扫描用户抽奖奖品发放MQ状态[Table = 2*4] 结束 dbCount not exist");
                continue;
            }

            for (int i = 0; i < tbCount; i++) {

                // 扫描库表数据
                List<InvoiceVO> invoiceVOList = activityPartake.scanInvoiceMqState(dbCount, i);
                logger.info("扫描用户抽奖奖品发放MQ状态[Table = 2*4] 扫描库：{} 扫描表：{} 扫描数：{}", dbCount, i, invoiceVOList.size());

                // 补偿 MQ 消息
                for (InvoiceVO invoiceVO : invoiceVOList) {
                    ListenableFuture<SendResult<String, Object>> future = kafkaProducer.sendLotteryInvoice(invoiceVO);
                    future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
                        @Override
                        public void onFailure(Throwable throwable) {
                            // MQ 消息发送失败，更新数据库表 user_strategy_export.mq_state = 2 【等待定时任务扫码补偿MQ消息】
                            activityPartake.updateInvoiceMqState(invoiceVO.getuId(), invoiceVO.getOrderId(), Constants.MQState.FAIL.getCode());
                        }

                        @Override
                        public void onSuccess(SendResult<String, Object> stringObjectSendResult) {
                            // MQ 消息发送完成，更新数据库表 user_strategy_export.mq_state = 1
                            activityPartake.updateInvoiceMqState(invoiceVO.getuId(), invoiceVO.getOrderId(), Constants.MQState.COMPLETE.getCode());
                        }
                    });
                }
            }

            logger.info("扫描用户抽奖奖品发放MQ状态[Table = 2*4] 完成 param：{}", JSON.toJSONString(params));
        }
    }
}
