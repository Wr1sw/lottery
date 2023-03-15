package cn.wr1sw.lottery.domain.rule.service.engine;

import cn.wr1sw.lottery.domain.rule.model.req.DecisionMatterReq;
import cn.wr1sw.lottery.domain.rule.model.res.EngineResult;

/**
 * @author wr1sw
 * @version 1.0.0
 * @description
 */
public interface EngineFilter {

    /**
     * 规则过滤器接口
     *
     * @param matter      规则决策物料
     * @return            规则决策结果
     */
    EngineResult process(final DecisionMatterReq matter);

}
