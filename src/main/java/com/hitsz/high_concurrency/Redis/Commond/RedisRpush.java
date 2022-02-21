package com.hitsz.high_concurrency.Redis.Commond;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.KebabCaseStrategy;
import com.hitsz.high_concurrency.Redis.Interfaces.RedisWrite;
import com.hitsz.high_concurrency.Redis.prefix.prefix;
import com.hitsz.high_concurrency.Result.Result;

import redis.clients.jedis.Jedis;

public class RedisRpush extends RedisCommond implements RedisWrite{

    private prefix pre ;
    private Object value;

    public RedisRpush(prefix pre,Object value) {
        this.pre = pre;
        this.value = value;
    }

    @Override
    Result<?> doCommond(Jedis jedis) {
        String json = null;
        if(value.getClass() == String.class) json = (String) value;
        else json = ObjectToJson(value);
        jedis.rpush(pre.getPrefix(),json);
        return null;
    }

    @Override
    public String getKey() {
        return pre.getPrefix();
    }

    @Override
    public int hashCode() {
        return pre.hashCode();
    }
    
}
