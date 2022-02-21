package com.hitsz.high_concurrency.MQ;

import java.util.List;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

@Component
public class MessageListenB {
    public MessageListenerConcurrently getExaOrderListen() {
        MessageListenerConcurrently listen = new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,ConsumeConcurrentlyContext context) {
               for(MessageExt msg : msgs) {              
                  
               }
               return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        };
        return listen;
    }
}
