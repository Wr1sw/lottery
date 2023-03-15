package cn.wr1sw.lottery.test.application;

import cn.wr1sw.lottery.application.mq.producer.HelloProducer;
import cn.wr1sw.lottery.application.mq.producer.KafkaProducer;
import cn.wr1sw.lottery.common.Constants;
import cn.wr1sw.lottery.domain.activity.model.vo.InvoiceVO;
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
public class KafkaProducerTest {
    private Logger logger = LoggerFactory.getLogger(KafkaProducerTest.class);

    @Resource
    private KafkaProducer kafkaProducer;

    @Resource
    private HelloProducer helloProducer;
    @Test
    public void test_send() throws InterruptedException {
        InvoiceVO invoice = new InvoiceVO();
        invoice.setuId("fustack");
        invoice.setOrderId(1444540456057864192L);
        invoice.setAwardId("3");
        invoice.setAwardType(Constants.AwardType.DESC.getCode());
        invoice.setAwardName("Code");
        invoice.setAwardContent("苹果电脑");
        invoice.setShippingAddress(null);
        invoice.setExtInfo(null);

        kafkaProducer.sendLotteryInvoice(invoice);

        while (true){
            Thread.sleep(10000);
        }

    }

    @Test
    public void test_send_hello() throws InterruptedException {
        // 循环发送消息
        while (true) {
            InvoiceVO invoice = new InvoiceVO();
            invoice.setuId("fustack");
            invoice.setOrderId(1444540456057864192L);
            invoice.setAwardId("3");
            invoice.setAwardType(Constants.AwardType.DESC.getCode());
            invoice.setAwardName("Code");
            invoice.setAwardContent("苹果电脑");
            invoice.setShippingAddress(null);
            invoice.setExtInfo(null);

            helloProducer.sendLotteryInvoice(invoice);
            Thread.sleep(3500);
        }
    }

}
