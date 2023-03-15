package cn.wr1sw.lottery.domain.rule.service.engine;

import cn.wr1sw.lottery.domain.rule.service.logic.LogicFilter;
import cn.wr1sw.lottery.domain.rule.service.logic.impl.UserAgeFilter;
import cn.wr1sw.lottery.domain.rule.service.logic.impl.UserGenderFilter;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wr1sw
 * @version 1.0.0
 * @description
 */
public class EngineConfig {

    protected static Map<String, LogicFilter> logicFilterMap = new ConcurrentHashMap<>();

    @Resource
    private UserAgeFilter userAgeFilter;
    @Resource
    private UserGenderFilter userGenderFilter;

    @PostConstruct
    public void init() {
        logicFilterMap.put("userAge", userAgeFilter);
        logicFilterMap.put("userGender", userGenderFilter);
    }

}
