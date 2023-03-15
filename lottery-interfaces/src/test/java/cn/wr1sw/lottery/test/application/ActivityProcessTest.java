package cn.wr1sw.lottery.test.application;

import cn.wr1sw.lottery.application.process.draw.IActivityDrawProcess;
import cn.wr1sw.lottery.application.process.draw.req.DrawProcessReq;
import cn.wr1sw.lottery.application.process.draw.res.DrawProcessResult;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author wr1sw
 * @version 1.0.0
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivityProcessTest {

    private Logger logger = LoggerFactory.getLogger(ActivityProcessTest.class);

    @Resource
    private IActivityDrawProcess activityProcess;

    @Test
    public void test_doDrawProcess() {
        DrawProcessReq req = new DrawProcessReq();
        req.setuId("xiaofuge");
        req.setActivityId(100001L);
//        int k = 8;
//        while (k != 0) {
//            k--;
//            DrawProcessResult drawProcessResult = activityProcess.doDrawProcess(req);
//            logger.info("请求入参：{}", JSON.toJSONString(req));
//            logger.info("测试结果：{}", JSON.toJSONString(drawProcessResult));
//        }
        DrawProcessResult drawProcessResult = activityProcess.doDrawProcess(req);
        logger.info("请求入参：{}", JSON.toJSONString(req));
        logger.info("测试结果：{}", JSON.toJSONString(drawProcessResult));


    }


}
