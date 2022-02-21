package com.hitsz.high_concurrency.Service;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.hitsz.high_concurrency.Data.Order.OrderInfo;
import com.hitsz.high_concurrency.Redis.RedisCommondFactory;
import com.hitsz.high_concurrency.Redis.Commond.RedisCommond;
import com.hitsz.high_concurrency.Redis.Key.MQKey;
import com.hitsz.high_concurrency.Redis.RedisServer.RedisService;
import com.hitsz.high_concurrency.Result.Result;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;

//用于发送失败重新发送消息
@Service
public class RedoService {
    
    @Resource(name = "miaoShaProducer")
    private DefaultMQProducer producer; 
    
    //用于写入订单
    @Autowired
    private OrderDaoService oService;
    @Autowired
    private RedisService rService;
    
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);

    public void redoMessgae(Message msg) {
        redoTask r = new redoTask(msg);
        executor.schedule(r,30,TimeUnit.SECONDS);
    }
   




    private class redoTask implements Runnable {
        
        private Message msg;
        public redoTask(Message msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            try {
               SendResult result = producer.send(msg);
               if(result.getSendStatus() != SendStatus.SEND_OK) {
                  redo(msg);
               }
            } catch (Exception e) {
               redo(msg);
            }
        }
        
        private void redo(Message msg) {
            boolean success = false;
            byte[] data = msg.getBody();
            OrderInfo info = RedisCommond.jsonToObject(new String(data), OrderInfo.class);
            //判断消息是否被消费过       
            RedisCommond get = RedisCommondFactory.getRedisGet(MQKey.msgId,info.getUserId() + " " + info.getGoodsId(),String.class);
            get.disableJudge();
            try {               
               Result<?> result = rService.doRedisCommond(get);
               if(result == null) {
                   return;
               }
             } catch (Exception e) {
                 e.printStackTrace();
             }
             success = oService.setOrder(info);
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
    }
}
