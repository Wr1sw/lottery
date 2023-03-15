package cn.wr1sw.lottery.domain.strategy.service.draw;

import cn.wr1sw.lottery.domain.strategy.model.aggregation.StrategyRich;
import cn.wr1sw.lottery.domain.strategy.model.vo.AwardBriefVO;
import cn.wr1sw.lottery.domain.strategy.repository.IStrategyRepository;


import javax.annotation.Resource;

/**
 * @author wr1sw
 * @version 1.0.0
 * @description
 */
public class DrawStrategySupport extends DrawConfig {

    @Resource
    protected IStrategyRepository strategyRepository;

    /**
     * 查询策略配置信息
     * @param strategyId
     * @return
     */
    protected StrategyRich queryStrategyRich(Long strategyId) {
        return strategyRepository.queryStrategyRich(strategyId);
    }

    /**
     * 查询奖品详情信息
     *
     * @param awardId 奖品ID
     * @return 中奖详情
     */
    protected AwardBriefVO queryAwardInfoByAwardId(String awardId){
        return strategyRepository.queryAwardInfo(awardId);
    }

}
