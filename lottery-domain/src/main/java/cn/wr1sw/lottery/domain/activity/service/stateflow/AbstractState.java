package cn.wr1sw.lottery.domain.activity.service.stateflow;

import cn.wr1sw.lottery.common.Constants;
import cn.wr1sw.lottery.common.Result;
import cn.wr1sw.lottery.domain.activity.repository.IActivityRepository;

import javax.annotation.Resource;

/**
 * @author wr1sw
 * @version 1.0.0
 * @description
 */
public abstract class AbstractState {

    @Resource
    protected IActivityRepository activityRepository;

    /**
     * 活动提审
     * @param activityId    活动ID
     * @param currentState  当前状态
     * @return              执行结果
     */
    public abstract Result arraignment(Long activityId, Enum<Constants.ActivityState> currentState);

    /**
     * 审核通过
     * @param activityId    活动ID
     * @param currentState  当前状态
     * @return              执行结果
     */
    public abstract Result checkPass(Long activityId, Enum<Constants.ActivityState> currentState);

    /**
     * 审核拒绝
     * @param activityId    活动ID
     * @param currentState  当前状态
     * @return              执行结果
     */
    public abstract Result checkRefuse(Long activityId, Enum<Constants.ActivityState> currentState);

    /**
     * 撤审撤销
     * @param activityId    活动ID
     * @param currentState  当前状态
     * @return              执行结果
     */
    public abstract Result checkRevoke(Long activityId, Enum<Constants.ActivityState> currentState);

    /**
     * 活动关闭
     * @param activityId    活动ID
     * @param currentState  当前状态
     * @return              执行结果
     */
    public abstract Result close(Long activityId, Enum<Constants.ActivityState> currentState);

    /**
     * 活动开启
     * @param activityId    活动ID
     * @param currentState  当前状态
     * @return              执行结果
     */
    public abstract Result open(Long activityId, Enum<Constants.ActivityState> currentState);

    /**
     * 活动执行
     * @param activityId    活动ID
     * @param currentState  当前状态
     * @return              执行结果
     */
    public abstract Result doing(Long activityId, Enum<Constants.ActivityState> currentState);
}
