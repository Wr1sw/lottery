package cn.wr1sw.lottery.application.process.draw.req;

/**
 * @author wr1sw
 * @version 1.0.0
 * @description 抽奖请求
 */
public class DrawProcessReq {

    /** 用户ID */
    private String uId;
    /** 活动ID */
    private Long activityId;

    public DrawProcessReq() {
    }

    public DrawProcessReq(String uId, Long activityId) {
        this.uId = uId;
        this.activityId = activityId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

}
