package com.hitsz.high_concurrency.Redis.Commond;

import com.hitsz.high_concurrency.Redis.Interfaces.RedisRead;
import com.hitsz.high_concurrency.Redis.prefix.prefix;
import com.hitsz.high_concurrency.Result.Result;

import redis.clients.jedis.Jedis;

public class RedisGet extends RedisCommond implements RedisRead{
    private prefix pre;
    private String key;
    private Class<?> clazz;

    public <T> RedisGet(prefix pre,String key,Class<T> clazz) {
        this.pre = pre;
        this.key = key;
        this.clazz = clazz;

    }
    @Override
    Result<?> doCommond(Jedis jedis) {
        String json = jedis.get(pre.getPrefix() + key);
        if(json == null) return null;
        if(clazz == String.class) {
            return Result.success(json);
        } else {
            return Result.success(jsonToObject(json, clazz));
        }

    }
    @Override
    public String getKey() {
        return pre.getPrefix() + key;
    }
    @Override
    public int hashCode() {
        return pre.hashCode() + key.hashCode();
    }
    
}
