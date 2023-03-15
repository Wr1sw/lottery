package cn.wr1sw.lottery.application.process.draw.res;

import cn.wr1sw.lottery.common.Result;
import cn.wr1sw.lottery.domain.strategy.model.vo.DrawAwardVo;

/**
 * @author wr1sw
 * @version 1.0.0
 * @description 活动抽奖结果
 */
public class DrawProcessResult extends Result {

    private DrawAwardVo drawAwardVo;

    public DrawProcessResult(String code, String info) {
        super(code, info);
    }

    public DrawProcessResult(String code, String info, DrawAwardVo drawAwardVo) {
        super(code, info);
        this.drawAwardVo = drawAwardVo;
    }

    public DrawAwardVo getDrawAwardVo() {
        return drawAwardVo;
    }

    public void setDrawAwardVo(DrawAwardVo drawAwardVo) {
        this.drawAwardVo = drawAwardVo;
    }

}
