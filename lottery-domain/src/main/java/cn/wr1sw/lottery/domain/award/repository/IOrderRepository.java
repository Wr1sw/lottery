package cn.wr1sw.lottery.domain.award.repository;

/**
 * @author wr1sw
 * @version 1.0.0
 * @description
 */
public interface IOrderRepository {

    /**
     * 更新奖品发放状态
     *
     * @param uId               用户ID
     * @param orderId           订单ID
     * @param awardId           奖品ID
     * @param grantState        奖品状态
     */
    void updateUserAwardState(String uId, Long orderId, String awardId, Integer grantState);
}
