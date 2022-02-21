package com.hitsz.high_concurrency.Redis.Commond;

import com.hitsz.high_concurrency.Result.Result;

import redis.clients.jedis.Jedis;

public class RedisFlushDB extends RedisCommond {

    public RedisFlushDB() {

    }

    @Override
    Result<?> doCommond(Jedis jedis) {
        jedis.flushDB();
        return null;
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return 0;
    }
    
}
