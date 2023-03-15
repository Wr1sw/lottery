package cn.wr1sw.lottery.rpc.activity.booth.res;


import cn.wr1sw.lottery.common.Result;
import cn.wr1sw.lottery.rpc.activity.booth.dto.AwardDTO;

import java.io.Serializable;

/**
 * @author wr1sw
 * @version 1.0.0
 * @description
 */
public class DrawRes extends Result implements Serializable {

    private AwardDTO awardDTO;

    public DrawRes(String code, String info) {
        super(code, info);
    }

    public AwardDTO getAwardDTO() {
        return awardDTO;
    }

    public void setAwardDTO(AwardDTO awardDTO) {
        this.awardDTO = awardDTO;
    }

}

