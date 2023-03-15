package cn.wr1sw.lottery.domain.award.service.factory;

import cn.wr1sw.lottery.domain.award.service.goods.IDistributionGoods;
import org.springframework.stereotype.Service;

/**
 * @author wr1sw
 * @version 1.0.0
 * @description
 */
@Service
public class DistributionGoodsFactory extends GoodsConfig {

    public IDistributionGoods getDistributionGoodsService(Integer awardType) {
        return goodsMap.get(awardType);
    }
}
