package com.hitsz.high_concurrency.Ping;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import com.fasterxml.jackson.core.sym.Name;
import com.hitsz.high_concurrency.Ping.Interfaces.Observer;
import com.hitsz.high_concurrency.Redis.RedisCommondFactory;
import com.hitsz.high_concurrency.Redis.redisPoolService;
import com.hitsz.high_concurrency.Redis.Commond.RedisCommond;
import com.hitsz.high_concurrency.Redis.Key.GoodsKey;
import com.hitsz.high_concurrency.Redis.LUA.Store;
import com.hitsz.high_concurrency.Redis.RedisServer.RedisRecoverService;
import com.hitsz.high_concurrency.Result.Result;
import com.hitsz.high_concurrency.Util.FinalValue;
import com.hitsz.high_concurrency.Util.MQUtil;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.MessageQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


//该类不需要并发安全，因为 Ping 机制和 Observer 是同步的
@Component
public class RedisObserver implements Observer{

    private boolean isClosed = false;
    @Autowired
    private redisPoolService redisPoolService;
    @Autowired
    @Qualifier("miaoShaProducer")
    private DefaultMQProducer producer;

    @Autowired
    private RedisRecoverService recoverService;

    @Override
    public void doWhenSuccess(){    
        //如果没宕机，不需要任何操作
        if(isClosed == false) return;
        int sendNum = 0;
        List<MessageQueue> list = null;
        try {
            System.out.println("检测宕机成功");
            //获取每个队列的最大消息数作为发送的总数
            list = producer.fetchPublishMessageQueues(MQUtil.MiaoShaOrderTopic);          
            for(MessageQueue queue : list) {
               sendNum += producer.maxOffset(queue);
            }   
            //重新建立 Redis 连接
           redisPoolService.updateJedisPool();
           //加载LRU脚本
           loadLRU();

           //计算实际的剩余库存并写入 Redis 
           int storeLast = FinalValue.TOTAL_STORE - sendNum;
           RedisCommond set = RedisCommondFactory.getRedisSet(GoodsKey.MiaoShaGoods,1+"",storeLast);
           recoverService.doRedisCommond(set);
           isClosed = false;
           System.out.println("重新连接成功 计算的库存为 " + storeLast + " " + sendNum);
        } catch (Exception e) {
           if(e.getMessage().contains("Can not find Message Queue for this topic")) {
               isClosed = false;
           }
              
        }

    }
    private void loadLRU() throws Exception {
        RedisCommond loadLUA = RedisCommondFactory.getRedisLoadLUA(Store.decrStoreInMiaoSha);        
        Result<?> result = recoverService.doRedisCommond(loadLUA);        
        // 缓存 LUA 脚本
        String code = (String)result.getData();
        Store.setDecrCode(code);
    }
    @Override
    public void doWhenFailOnce() {
        
    }

    @Override
    public void doWhenShutDown() {
        if(isClosed) return;
        isClosed = true;
    }  

    public boolean getClosed() {
        return isClosed;
    }
}
