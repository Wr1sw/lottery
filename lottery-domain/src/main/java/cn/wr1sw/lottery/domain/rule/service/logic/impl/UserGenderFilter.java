package cn.wr1sw.lottery.domain.rule.service.logic.impl;

import cn.wr1sw.lottery.domain.rule.model.req.DecisionMatterReq;
import cn.wr1sw.lottery.domain.rule.service.logic.BaseLogic;
import org.springframework.stereotype.Component;

/**
 * @author wr1sw
 * @version 1.0.0
 * @description
 */
@Component
public class UserGenderFilter extends BaseLogic {

    @Override
    public String matterValue(DecisionMatterReq decisionMatter) {
        return decisionMatter.getValMap().get("gender").toString();
    }

}
