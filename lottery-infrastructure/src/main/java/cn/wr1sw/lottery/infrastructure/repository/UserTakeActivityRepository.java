package cn.wr1sw.lottery.infrastructure.repository;

import cn.wr1sw.lottery.common.Constants;
import cn.wr1sw.lottery.domain.activity.model.vo.ActivityPartakeRecordVO;
import cn.wr1sw.lottery.domain.activity.model.vo.DrawOrderVO;
import cn.wr1sw.lottery.domain.activity.model.vo.InvoiceVO;
import cn.wr1sw.lottery.domain.activity.model.vo.UserTakeActivityVO;
import cn.wr1sw.lottery.domain.activity.repository.IUserTakeActivityRepository;
import cn.wr1sw.lottery.infrastructure.dao.IActivityDao;
import cn.wr1sw.lottery.infrastructure.dao.IUserStrategyExportDao;
import cn.wr1sw.lottery.infrastructure.dao.IUserTakeActivityCountDao;
import cn.wr1sw.lottery.infrastructure.dao.IUserTakeActivityDao;
import cn.wr1sw.lottery.infrastructure.po.Activity;
import cn.wr1sw.lottery.infrastructure.po.UserStrategyExport;
import cn.wr1sw.lottery.infrastructure.po.UserTakeActivity;
import cn.wr1sw.lottery.infrastructure.po.UserTakeActivityCount;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wr1sw
 * @version 1.0.0
 * @description
 */
@Repository
public class UserTakeActivityRepository implements IUserTakeActivityRepository {

    @Resource
    private IUserTakeActivityCountDao userTakeActivityCountDao;

    @Resource
    private IUserTakeActivityDao userTakeActivityDao;

    @Resource
    private IActivityDao activityDao;

    @Resource
    private IUserStrategyExportDao userStrategyExportDao;

    @Override
    public int subtractionLeftCount(Long activityId, String activityName, Integer takeCount, Integer userTakeLeftCount, String uId) {
        if (null == userTakeLeftCount) {
            UserTakeActivityCount userTakeActivityCount = new UserTakeActivityCount();
            userTakeActivityCount.setuId(uId);
            userTakeActivityCount.setActivityId(activityId);
            userTakeActivityCount.setTotalCount(takeCount);
            userTakeActivityCount.setLeftCount(takeCount - 1);
            userTakeActivityCountDao.insert(userTakeActivityCount);
            return 1;
        } else {
            UserTakeActivityCount userTakeActivityCount = new UserTakeActivityCount();
            userTakeActivityCount.setuId(uId);
            userTakeActivityCount.setActivityId(activityId);
            return userTakeActivityCountDao.updateLeftCount(userTakeActivityCount);
        }

    }

    @Override
    public void takeActivity(Long activityId, String activityName, Long strategyId, Integer takeCount, Integer userTakeLeftCount, String uId, Date takeDate, Long takeId) {
        UserTakeActivity userTakeActivity = new UserTakeActivity();
        userTakeActivity.setuId(uId);
        userTakeActivity.setTakeId(takeId);
        userTakeActivity.setActivityId(activityId);
        userTakeActivity.setActivityName(activityName);
        userTakeActivity.setTakeDate(takeDate);
        if (null == userTakeLeftCount) {
            userTakeActivity.setTakeCount(1);
        } else {
            userTakeActivity.setTakeCount(takeCount - userTakeLeftCount + 1);
        }
        userTakeActivity.setStrategyId(strategyId);
        userTakeActivity.setState(Constants.TaskState.NO_USED.getCode());
        String uuid = activityId + "_" + uId + "_" + userTakeActivity.getTakeCount();
        userTakeActivity.setUuid(uuid);

        userTakeActivityDao.insert(userTakeActivity);

    }

    @Override
    public int lockTackActivity(String uId, Long activityId, Long takeId) {
        UserTakeActivity userTakeActivity = new UserTakeActivity();
        userTakeActivity.setuId(uId);
        userTakeActivity.setActivityId(activityId);
        userTakeActivity.setTakeId(takeId);
        return userTakeActivityDao.lockTackActivity(userTakeActivity);
    }

    @Override
    public void saveUserStrategyExport(DrawOrderVO drawOrder) {
        UserStrategyExport userStrategyExport = new UserStrategyExport();
        userStrategyExport.setuId(drawOrder.getuId());
        userStrategyExport.setActivityId(drawOrder.getActivityId());
        userStrategyExport.setOrderId(drawOrder.getOrderId());
        userStrategyExport.setStrategyId(drawOrder.getStrategyId());
        userStrategyExport.setStrategyMode(drawOrder.getStrategyMode());
        userStrategyExport.setGrantType(drawOrder.getGrantType());
        userStrategyExport.setGrantDate(drawOrder.getGrantDate());
        userStrategyExport.setGrantState(drawOrder.getGrantState());
        userStrategyExport.setAwardId(drawOrder.getAwardId());
        userStrategyExport.setAwardType(drawOrder.getAwardType());
        userStrategyExport.setAwardName(drawOrder.getAwardName());
        userStrategyExport.setAwardContent(drawOrder.getAwardContent());
        userStrategyExport.setUuid(String.valueOf(drawOrder.getTakeId()));

        userStrategyExportDao.insert(userStrategyExport);

    }

    @Override
    public UserTakeActivityVO queryNoConsumedTakeActivityOrder(Long activityId, String uId) {
        UserTakeActivity userTakeActivity = new UserTakeActivity();
        userTakeActivity.setuId(uId);
        userTakeActivity.setActivityId(activityId);
        UserTakeActivity noConsumedTakeActivityOrder = userTakeActivityDao.queryNoConsumedTakeActivityOrder(userTakeActivity);

        // 未查询到符合的领取单，直接返回 NULL
        if (null == noConsumedTakeActivityOrder) {
            return null;
        }

        UserTakeActivityVO userTakeActivityVO = new UserTakeActivityVO();
        userTakeActivityVO.setActivityId(noConsumedTakeActivityOrder.getActivityId());
        userTakeActivityVO.setTakeId(noConsumedTakeActivityOrder.getTakeId());
        userTakeActivityVO.setStrategyId(noConsumedTakeActivityOrder.getStrategyId());
        userTakeActivityVO.setState(noConsumedTakeActivityOrder.getState());

        return userTakeActivityVO;

    }

    @Override
    public void updateInvoiceMqState(String uId, Long orderId, Integer mqState) {
        UserStrategyExport userStrategyExport = new UserStrategyExport();
        userStrategyExport.setuId(uId);
        userStrategyExport.setOrderId(orderId);
        userStrategyExport.setMqState(mqState);
        userStrategyExportDao.updateInvoiceMqState(userStrategyExport);
    }

    @Override
    public List<InvoiceVO> scanInvoiceMqState() {
        // 查询发送MQ失败和超时30分钟，未发送MQ的数据
        List<UserStrategyExport> userStrategyExportList = userStrategyExportDao.scanInvoiceMqState();

        // 转换对象
        List<InvoiceVO> invoiceVOList = new ArrayList<>(userStrategyExportList.size());
        for (UserStrategyExport userStrategyExport : userStrategyExportList) {
            InvoiceVO invoiceVO = new InvoiceVO();
            invoiceVO.setuId(userStrategyExport.getuId());
            invoiceVO.setOrderId(userStrategyExport.getOrderId());
            invoiceVO.setAwardId(userStrategyExport.getAwardId());
            invoiceVO.setAwardType(userStrategyExport.getAwardType());
            invoiceVO.setAwardName(userStrategyExport.getAwardName());
            invoiceVO.setAwardContent(userStrategyExport.getAwardContent());
            invoiceVOList.add(invoiceVO);
        }
        return invoiceVOList;

    }

    @Override
    public void updateActivityStock(ActivityPartakeRecordVO activityPartakeRecordVO) {
        Activity activity = new Activity();
        activity.setActivityId(activityPartakeRecordVO.getActivityId());
        activity.setStockSurplusCount(activityPartakeRecordVO.getStockSurplusCount());
        activityDao.updateActivityStock(activity);

    }
}
