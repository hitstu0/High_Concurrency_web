package com.hitsz.high_concurrency.Redis.Commond;

import com.hitsz.high_concurrency.Redis.Interfaces.RedisRead;
import com.hitsz.high_concurrency.Redis.prefix.prefix;
import com.hitsz.high_concurrency.Result.Result;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class RedisDelete extends RedisCommond implements RedisRead{
    private prefix pre;
    private String key;
    public RedisDelete(prefix pre,String key) {
        this.pre = pre;
        this.key = key;
    }
    @Override
    Result<?> doCommond(Jedis jedis) throws JedisConnectionException {
        jedis.del(pre.getPrefix() + key);
        return null;
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
