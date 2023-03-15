package cn.wr1sw.lottery.domain.activity.model.req;

import cn.wr1sw.lottery.common.PageRequest;

/**
 * @author wr1sw
 * @version 1.0.0
 * @description
 */
public class ActivityInfoLimitPageReq extends PageRequest {

    /**
     * 活动ID
     */
    private Long activityId;

    /**
     * 活动名称
     */
    private String activityName;

    public ActivityInfoLimitPageReq(int page, int rows) {
        super(page, rows);
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

}
