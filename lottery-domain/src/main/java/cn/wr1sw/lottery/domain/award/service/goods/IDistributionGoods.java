package cn.wr1sw.lottery.domain.award.service.goods;

import cn.wr1sw.lottery.domain.award.model.req.GoodsReq;
import cn.wr1sw.lottery.domain.award.model.res.DistributionRes;

/**
 * @author wr1sw
 * @version 1.0.0
 * @description
 */
public interface IDistributionGoods {

    /**
     * 奖品配送接口，奖品类型（1：文字描述、2：兑换码、3：优惠券、4：实物奖品）
     *
     * @param req 物品信息
     * @return    配送结果
     */
    DistributionRes doDistribution(GoodsReq req);
}
