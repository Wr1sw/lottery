package cn.wr1sw.lottery.domain.award.service.goods.impl;

import cn.wr1sw.lottery.common.Constants;
import cn.wr1sw.lottery.domain.award.model.req.GoodsReq;
import cn.wr1sw.lottery.domain.award.model.res.DistributionRes;
import cn.wr1sw.lottery.domain.award.service.goods.DistributionBase;
import cn.wr1sw.lottery.domain.award.service.goods.IDistributionGoods;
import org.springframework.stereotype.Component;

/**
 * @author wr1sw
 * @version 1.0.0
 * @description
 */
@Component("descGoods")
public class DescGoods extends DistributionBase implements IDistributionGoods {

    @Override
    public DistributionRes doDistribution(GoodsReq req) {

        super.updateUserAwardState(req.getuId(), req.getOrderId(), req.getAwardId(), Constants.GrantState.COMPLETE.getCode());

        return new DistributionRes(req.getuId(), Constants.AwardState.SUCCESS.getCode(), Constants.AwardState.SUCCESS.getInfo());
    }
}
