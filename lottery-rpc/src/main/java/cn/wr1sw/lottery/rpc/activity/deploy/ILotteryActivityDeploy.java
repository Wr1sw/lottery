package cn.wr1sw.lottery.rpc.activity.deploy;

import cn.wr1sw.lottery.rpc.activity.deploy.req.ActivityPageReq;
import cn.wr1sw.lottery.rpc.activity.deploy.res.ActivityRes;

/**
 * @author wr1sw
 * @version 1.0.0
 * @description 抽奖活动部署服务接口
 */
public interface ILotteryActivityDeploy {
    /**
     * 通过分页查询活动列表信息，给ERP运营使用
     *
     * @param req   查询参数
     * @return      查询结果
     */
    ActivityRes queryActivityListByPageForErp(ActivityPageReq req);

}
