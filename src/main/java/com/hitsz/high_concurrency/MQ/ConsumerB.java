package com.hitsz.high_concurrency.MQ;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import com.hitsz.high_concurrency.Util.MQUtil;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListener;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Component
public class ConsumerB {
    @Autowired
    private MessageListenB listenB;
    
    public static DefaultMQPushConsumer getConsumer(String group,String topic,String tag,MessageListenerConcurrently listener) throws MQClientException{
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(group);
        consumer.setNamesrvAddr(MQUtil.NameServerAdr);
        consumer.subscribe(topic,tag);
        consumer.registerMessageListener(listener);  
        return consumer;
    }
    
  

    @Bean(name = "payOrderConsumer")
    public DefaultMQPushConsumer payOrderConsumer() throws MQClientException {
        DefaultMQPushConsumer consumer = getConsumer(MQUtil.exaOrderGroup,MQUtil.PayedOrderTopic,MQUtil.PayedOrderTag,listenB.getExaOrderListen());
        consumer.start();
        return consumer;
    }
}
