package com.hitsz.high_concurrency.MQ;

import java.util.List;
import com.hitsz.high_concurrency.Data.Order.OrderInfo;
import com.hitsz.high_concurrency.Redis.RedisCommondFactory;
import com.hitsz.high_concurrency.Redis.Commond.RedisCommond;
import com.hitsz.high_concurrency.Redis.Key.MQKey;
import com.hitsz.high_concurrency.Redis.RedisServer.RedisService;
import com.hitsz.high_concurrency.Result.Result;
import com.hitsz.high_concurrency.Service.OrderDaoService;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MessageListen {
   
    @Autowired
    private OrderDaoService oDaoService;
    @Autowired
    private RedisService rService;

    public MessageListenerConcurrently getOrderMsgListen() {    
        MessageListenerConcurrently listen = new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,ConsumeConcurrentlyContext context){
               boolean success = false;
               for(MessageExt msg : msgs) {     
                   byte[] data = msg.getBody();
                   OrderInfo info = RedisCommond.jsonToObject(new String(data), OrderInfo.class);
                   //判断消息是否被消费过       
                   RedisCommond get = RedisCommondFactory.getRedisGet(MQKey.msgId,info.getUserId() + " " + info.getGoodsId(),String.class);
                   get.disableJudge();
                   try {               
                      Result<?> result = rService.doRedisCommond(get);
                      if(result == null) {
                          success = true;
                          continue; 
                      }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    success = oDaoService.setOrder(info);
                    if(success) {
                        RedisCommond del = RedisCommondFactory.getRedisDelete(MQKey.msgId,info.getUserId() + " " + info.getGoodsId());
                        del.disableJudge();
                        try {
                            rService.doRedisCommond(del);
                        } catch (Exception e) {
                           e.printStackTrace();
                        }
                    }              
                }
               if(success) 
                  return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
               else 
                  return ConsumeConcurrentlyStatus.RECONSUME_LATER;  
           }
        };
        return listen;
    }

  
}
