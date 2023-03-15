package cn.wr1sw.lottery.application.mq.producer;

import cn.wr1sw.lottery.domain.activity.model.vo.InvoiceVO;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;


import javax.annotation.Resource;


/**
 * @author wr1sw
 * @version 1.0.0
 * @description
 */
@Component
public class HelloProducer {

    private Logger logger = LoggerFactory.getLogger(HelloProducer.class);

    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;

    public static final String TOPIC_TEST = "Hello-Kafka";

    public static final String TOPIC_GROUP = "test-consumer-group";

    public ListenableFuture<SendResult<String, Object>> sendLotteryInvoice(InvoiceVO invoice) {
        String objJson = JSON.toJSONString(invoice);
        logger.info("发送MQ消息 topic：{} bizId：{} message：{}", TOPIC_TEST, invoice.getuId(), objJson);
        return kafkaTemplate.send(TOPIC_TEST, objJson);
    }

}
