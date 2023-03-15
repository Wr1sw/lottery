package cn.wr1sw.lottery.rpc.activity.booth;

import cn.wr1sw.lottery.rpc.activity.booth.req.DrawReq;
import cn.wr1sw.lottery.rpc.activity.booth.req.QuantificationDrawReq;
import cn.wr1sw.lottery.rpc.activity.booth.res.DrawRes;

/**
 * @author wr1sw
 * @version 1.0.0
 * @description
 */
public interface ILotteryActivityBooth {

    /**
     * 指定活动抽奖
     * @param drawReq 请求参数
     * @return        抽奖结果
     */
    DrawRes doDraw(DrawReq drawReq);

    /**
     * 量化人群抽奖
     * @param quantificationDrawReq 请求参数
     * @return                      抽奖结果
     */
    DrawRes doQuantificationDraw(QuantificationDrawReq quantificationDrawReq);

}
