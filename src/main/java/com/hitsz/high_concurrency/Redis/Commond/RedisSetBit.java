package com.hitsz.high_concurrency.Redis.Commond;

import com.hitsz.high_concurrency.Redis.Key.GoodsKey;
import com.hitsz.high_concurrency.Result.Result;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class RedisSetBit extends RedisCommond{
    private String key;
    private int index;
    private boolean value;
    public RedisSetBit(GoodsKey miaoShaCache,int index,boolean value) {
         this.key = miaoShaCache.getPrefix();
         this.index = index;
         this.value = value;
    }

    @Override
    Result<?> doCommond(Jedis jedis) throws JedisConnectionException {
        jedis.setbit(key,index, value);
        return null;
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return 0;
    }
}
