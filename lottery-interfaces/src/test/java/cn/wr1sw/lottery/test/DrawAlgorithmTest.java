package cn.wr1sw.lottery.test;

import cn.wr1sw.lottery.common.Constants;
import cn.wr1sw.lottery.domain.strategy.model.req.DrawReq;
import cn.wr1sw.lottery.domain.strategy.model.res.DrawResult;
import cn.wr1sw.lottery.domain.strategy.model.vo.AwardRateInfo;
import cn.wr1sw.lottery.domain.strategy.service.algorithm.IDrawAlgorithm;
import cn.wr1sw.lottery.domain.strategy.service.draw.IDrawExec;
import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author wr1sw
 * @version 1.0.0
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DrawAlgorithmTest {

    private Logger logger = LoggerFactory.getLogger(SpringRunnerTest.class);

    @Resource(name = "entiretyRateRandomDrawAlgorithm")
    private IDrawAlgorithm randomDrawAlgorithm;

    @Resource(name = "drawExec")
    private IDrawExec iDrawExec;

    @Before
    public void init() {
        // 奖品信息
        List<AwardRateInfo> strategyList = new ArrayList<>();
        strategyList.add(new AwardRateInfo("一等奖：IMac", new BigDecimal("0.05")));
        strategyList.add(new AwardRateInfo("二等奖：iphone", new BigDecimal("0.1")));
        strategyList.add(new AwardRateInfo("三等奖：ipad", new BigDecimal("0.25")));
        strategyList.add(new AwardRateInfo("四等奖：AirPods", new BigDecimal("0.25")));
        strategyList.add(new AwardRateInfo("五等奖：充电宝", new BigDecimal("0.35")));

        // 初始数据
        randomDrawAlgorithm.initRateTuple(100001L, Constants.StrategyMode.SINGLE.getCode(), strategyList);
    }

    @Test
    public void test_randomDrawAlgorithm() {

        List<String> excludeAwardIds = new ArrayList<>();
//        excludeAwardIds.add("二等奖：iphone");
//        excludeAwardIds.add("四等奖：AirPods");

        int userNum = 30000; // 日活用户数
        int drawNum = userNum * 3; // 每天抽奖次数 = 日活数*抽奖次数
        for (int i = 0; i < drawNum; i++) {
            System.out.println("中奖结果：" + randomDrawAlgorithm.randomDraw(100001L, excludeAwardIds));
        }

    }

    @Test
    public void test_iDrawExec() {
        DrawResult drawResult = iDrawExec.doDrawExec(new DrawReq("小傅哥", 10001L));
        logger.info("测试结果：{}", JSON.toJSONString(drawResult));
    }

    @Test
    public void test111() {
        final Map<String, Integer> awardStockMap = new ConcurrentHashMap<>(); // 奖品 <--> 奖品库存
        awardStockMap.put("1", 5000);
        awardStockMap.put("2", 1000);
        awardStockMap.put("3", 500);
        awardStockMap.put("5", 100);
        awardStockMap.put("iphone", 1);
        awardStockMap.put("未中奖", 59409); //6601*10 -6601
        //权重默认等于库存
        final Map<String, Integer> awardWeightMap = new ConcurrentHashMap<>(awardStockMap); // 奖品 <--> 奖品权重

        int userNum = 30000; // 日活用户数
        int drawNum = userNum * 3; // 每天抽奖次数 = 日活数*抽奖次数
        Map<String, Integer> dailyWinCountMap = new ConcurrentHashMap<>(); // 每天实际中奖计数
        for(int j=0; j<drawNum; j++){ // 模拟每次抽奖
            //排除掉库存为0的奖品
            Map<String, Integer> awardWeightHaveStockMap = awardWeightMap.entrySet().stream().filter(e->awardStockMap.get(e.getKey())>0).collect(Collectors.toMap(e->e.getKey(), e->e.getValue()));
            int totalWeight = (int) awardWeightHaveStockMap.values().stream().collect(Collectors.summarizingInt(i->i)).getSum();
            int randNum = new Random().nextInt(totalWeight); //生成一个随机数
            int prev = 0;
            String choosedAward = null;
            // 按照权重计算中奖区间
            for(Map.Entry<String,Integer> e : awardWeightHaveStockMap.entrySet() ){
                if(randNum>=prev && randNum<prev+e.getValue()){
                    choosedAward = e.getKey(); //落入该奖品区间
                    break;
                }
                prev = prev+e.getValue();
            }
            dailyWinCountMap.compute(choosedAward, (k,v)->v==null?1:v+1); //中奖计数
            if(!"未中奖".equals(choosedAward)){ //未中奖不用减库存
                awardStockMap.compute(choosedAward, (k,v)->v-1); //奖品库存一
                if(awardStockMap.get(choosedAward)==0){
                    System.out.printf("奖品：%s 库存为空%n",choosedAward); //记录库存为空的顺序
                }
            }

        }
        System.out.println("各奖品中奖计数: "+dailyWinCountMap); //每日各奖品中奖计数
    }



}
