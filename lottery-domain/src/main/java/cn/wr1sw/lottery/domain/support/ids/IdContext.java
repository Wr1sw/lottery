package cn.wr1sw.lottery.domain.support.ids;

import cn.hutool.core.lang.Snowflake;
import cn.wr1sw.lottery.common.Constants;
import cn.wr1sw.lottery.domain.support.ids.policy.RandomNumeric;
import cn.wr1sw.lottery.domain.support.ids.policy.ShortCode;
import cn.wr1sw.lottery.domain.support.ids.policy.SnowFlake;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wr1sw
 * @version 1.0.0
 * @description
 */
@Configuration
public class IdContext {

    @Bean
    public Map<Constants.Ids, IIdGenerator> idGenerator(SnowFlake snowFlake, ShortCode shortCode, RandomNumeric randomNumeric) {
        Map<Constants.Ids, IIdGenerator> idGeneratorMap = new HashMap<>(8);
        idGeneratorMap.put(Constants.Ids.SnowFlake, snowFlake);
        idGeneratorMap.put(Constants.Ids.ShortCode, shortCode);
        idGeneratorMap.put(Constants.Ids.RandomNumeric, randomNumeric);
        return idGeneratorMap;
    }
}
