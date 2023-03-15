package cn.wr1sw.lottery.application.process.draw;


import cn.wr1sw.lottery.application.process.draw.req.DrawProcessReq;
import cn.wr1sw.lottery.application.process.draw.res.DrawProcessResult;
import cn.wr1sw.lottery.application.process.draw.res.RuleQuantificationCrowdResult;
import cn.wr1sw.lottery.domain.rule.model.req.DecisionMatterReq;

/**
 * @author wr1sw
 * @version 1.0.0
 * @description 活动抽奖流程编排接口
 */
public interface IActivityDrawProcess {

    /**
     * 执行抽奖流程
     * @param req 抽奖请求
     * @return    抽奖结果
     */
    DrawProcessResult doDrawProcess(DrawProcessReq req);

    /**
     * 规则量化人群，返回可参与的活动ID
     * @param req 规则请求
     * @return 量化结果，用户可以参与的活动ID
     */
    RuleQuantificationCrowdResult doRuleQuantificationCrowd(DecisionMatterReq req);

}
