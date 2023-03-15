package cn.wr1sw.lottery.application.process.draw.res;

import cn.wr1sw.lottery.common.Result;

/**
 * @author wr1sw
 * @version 1.0.0
 * @description
 */
public class RuleQuantificationCrowdResult extends Result {

    /** 活动ID */
    private Long activityId;

    public RuleQuantificationCrowdResult(String code, String info) {
        super(code, info);
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

}
