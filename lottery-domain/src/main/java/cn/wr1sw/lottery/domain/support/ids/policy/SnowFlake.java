package cn.wr1sw.lottery.domain.support.ids.policy;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import cn.wr1sw.lottery.domain.support.ids.IIdGenerator;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author wr1sw
 * @version 1.0.0
 * @description
 */
@Component
public class SnowFlake implements IIdGenerator {

    private Snowflake snowFlake;

    @PostConstruct
    public void init() {
        // 0 ~ 31位， 可以采用配置的方式使用
        long workerId;
        try {
            workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
        } catch (Exception e) {
            workerId = NetUtil.getLocalhostStr().hashCode();
        }

        workerId = workerId >> 16 & 31;

        long dataCenterId = 1L;
        snowFlake = IdUtil.createSnowflake(workerId, dataCenterId);
    }


    @Override
    public synchronized long nextId() {
        return snowFlake.nextId();
    }
}
