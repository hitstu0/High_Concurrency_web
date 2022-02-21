package com.hitsz.high_concurrency.MQ;



import com.hitsz.high_concurrency.Util.MQUtil;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
    
    @Autowired
    private MessageListen listen;
    public static DefaultMQPushConsumer getConsumer(String group,String topic,String tag,MessageListenerConcurrently listener) throws MQClientException{
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(group);
        consumer.setNamesrvAddr(MQUtil.NameServerAdr);
        consumer.subscribe(topic,tag);
        consumer.registerMessageListener(listener);  
        return consumer;
    }
    
    @Bean(name = "miaoShaConsumer")
    public DefaultMQPushConsumer[] miaoShaConsumer() throws MQClientException {
       DefaultMQPushConsumer[] result = new DefaultMQPushConsumer[4];
       DefaultMQPushConsumer consumer1 = getConsumer(MQUtil.consumerOrderGroup,MQUtil.MiaoShaOrderTopic,MQUtil.createOrderTag,listen.getOrderMsgListen());
       DefaultMQPushConsumer consumer2 = getConsumer(MQUtil.consumerOrderGroup,MQUtil.MiaoShaOrderTopic,MQUtil.createOrderTag,listen.getOrderMsgListen());
       DefaultMQPushConsumer consumer3 = getConsumer(MQUtil.consumerOrderGroup,MQUtil.MiaoShaOrderTopic,MQUtil.createOrderTag,listen.getOrderMsgListen());
       DefaultMQPushConsumer consumer4 = getConsumer(MQUtil.consumerOrderGroup,MQUtil.MiaoShaOrderTopic,MQUtil.createOrderTag,listen.getOrderMsgListen());
       //最多一次消费多少条数据的参数需要在压测中调整       
       consumer1.start();
       consumer2.start();
       consumer3.start();
       consumer4.start();
       result[0] = consumer1;
       result[1] = consumer2;
       result[2] = consumer3;
       result[3] = consumer4;
       return result;
    }
}

