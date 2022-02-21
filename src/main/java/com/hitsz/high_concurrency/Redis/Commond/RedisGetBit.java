package com.hitsz.high_concurrency.Redis.Commond;

import com.hitsz.high_concurrency.Redis.Key.GoodsKey;
import com.hitsz.high_concurrency.Result.Result;

import lombok.val;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class RedisGetBit extends RedisCommond {
    
    private String key;
    private int index;
    public RedisGetBit(GoodsKey miaoShaCache,int index) {
        this.index = index;
        this.key = miaoShaCache.getPrefix();
    }

    @Override
    Result<?> doCommond(Jedis jedis) throws JedisConnectionException {
        Boolean value = jedis.getbit(key,index);
        return Result.success(value);
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return 0;
    }
    
}
