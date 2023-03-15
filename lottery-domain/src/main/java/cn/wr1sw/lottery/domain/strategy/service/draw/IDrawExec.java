package cn.wr1sw.lottery.domain.strategy.service.draw;

import cn.wr1sw.lottery.domain.strategy.model.req.DrawReq;
import cn.wr1sw.lottery.domain.strategy.model.res.DrawResult;

/**
 * @author wr1sw
 * @version 1.0.0
 * @description
 */
public interface IDrawExec {

    /**
     * 抽奖方法
     * @param req 抽奖参数；用户ID、策略ID
     * @return    中奖结果
     */
    DrawResult doDrawExec(DrawReq req);
}
