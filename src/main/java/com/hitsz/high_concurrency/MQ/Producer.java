package com.hitsz.high_concurrency.MQ;

import java.util.List;

import javax.annotation.Resource;

import com.hitsz.high_concurrency.Util.MQUtil;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Producer {
    public static DefaultMQProducer getProducer(String group) throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer(group);
        producer.setNamesrvAddr(MQUtil.NameServerAdr);  
        producer.setSendMsgTimeout(60000);
        try {
            producer.start();
        } catch (MQClientException e) {
           throw e;
        }             
        return producer;
    }

    @Bean(name = "miaoShaProducer")
    public DefaultMQProducer miaoShaProducer() throws MQClientException {
        return getProducer(MQUtil.consumerOrderGroup);
    }

    @Bean(name = "payOrderProducer")
    public DefaultMQProducer payOrderProducer() throws MQClientException {
        return getProducer(MQUtil.PayedOrderGroup);
    }
}
