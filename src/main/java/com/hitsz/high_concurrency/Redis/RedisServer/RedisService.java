package com.hitsz.high_concurrency.Redis.RedisServer;


import com.hitsz.high_concurrency.Exeception.Base.ViewException;
import com.hitsz.high_concurrency.Ping.RedisObserver;
import com.hitsz.high_concurrency.Redis.RedisCommondFactory;
import com.hitsz.high_concurrency.Redis.Commond.RedisCommond;
import com.hitsz.high_concurrency.Redis.Interfaces.RedisRead;
import com.hitsz.high_concurrency.Redis.Interfaces.RedisWrite;
import com.hitsz.high_concurrency.Redis.Key.GoodsKey;
import com.hitsz.high_concurrency.Result.CodeMsg;
import com.hitsz.high_concurrency.Result.Result;
import com.hitsz.high_concurrency.Util.CommonMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;


@Service
public class RedisService extends AbstractRedisService {     

    @Autowired
    private RedisObserver redisObserver;

    @Autowired
    private RedisRecoverService recoverService;

    @Override
    void beforeGetResource(RedisCommond redisCommond) {
        if(redisObserver.getClosed()) {
            throw new ViewException(CodeMsg.SYSTEM_FAIL);
        }          
    }

    //在执行前执行
    @Override
    void beforeExcute(RedisCommond redisCommond, Jedis jedis) throws Exception {
        if(redisCommond.getJudge() == false) return;
        if(redisCommond instanceof RedisWrite) {
           //如果是写则加入 bitmap
           int hash1 = CommonMethod.hashOne(redisCommond);
           int hash2 = CommonMethod.hashTwo(redisCommond);
           int hash3 = CommonMethod.hashThree(redisCommond);
           RedisCommond setBit1 = RedisCommondFactory.getRedisSetBit(GoodsKey.MiaoShaCache,Math.abs(hash1),true);
           RedisCommond setBit2 = RedisCommondFactory.getRedisSetBit(GoodsKey.MiaoShaCache,Math.abs(hash2),true);
           RedisCommond setBit3 = RedisCommondFactory.getRedisSetBit(GoodsKey.MiaoShaCache,Math.abs(hash3),true);
           recoverService.doRedisCommond(setBit1);
           recoverService.doRedisCommond(setBit2);
           recoverService.doRedisCommond(setBit3);
        }
        if(redisCommond instanceof RedisRead) {
            //如果是读则先判断 bitmap
            int hash1 = CommonMethod.hashOne(redisCommond);
            int hash2 = CommonMethod.hashTwo(redisCommond);
            int hash3 = CommonMethod.hashThree(redisCommond);
            RedisCommond getBit1 = RedisCommondFactory.getRedisGetBit(GoodsKey.MiaoShaCache,Math.abs(hash1));
            RedisCommond getBit2 = RedisCommondFactory.getRedisGetBit(GoodsKey.MiaoShaCache,Math.abs(hash2));
            RedisCommond getBit3 = RedisCommondFactory.getRedisGetBit(GoodsKey.MiaoShaCache,Math.abs(hash3));
            Result<?> result1 = recoverService.doRedisCommond(getBit1);
            Result<?> result2 = recoverService.doRedisCommond(getBit2);
            Result<?> result3 = recoverService.doRedisCommond(getBit3);
            boolean bit1 = (boolean)result1.getData();
            boolean bit2 = (boolean)result2.getData();
            boolean bit3 = (boolean)result3.getData();
            if( !(bit1 && bit2 && bit3 ) ) {
                throw new ViewException(CodeMsg.URL_ERROR);
            } 
        }
    }

    @Override
    void afterExcute(RedisCommond redisCommond, Jedis jedis) {
        // TODO Auto-generated method stub
        
    }

    
}
