package com.hitsz.high_concurrency.Redis.Commond;

import com.hitsz.high_concurrency.Redis.Interfaces.RedisWrite;
import com.hitsz.high_concurrency.Redis.prefix.prefix;
import com.hitsz.high_concurrency.Result.Result;

import redis.clients.jedis.Jedis;

public class RedisSet extends RedisCommond implements RedisWrite{
      
     public prefix pre;
     private String key;
     private Object value;

     public RedisSet(prefix pre,String key,Object value) {
        this.pre = pre;
        this.key = key;
        this.value = value;
     }

    @Override
    Result<?> doCommond(Jedis jedis) {
        String json;
        if(value.getClass() == String.class) json = (String) value;
        else json = ObjectToJson(value);
        if(pre.getExpiredTime() == 0) jedis.set(pre.getPrefix()+key,json);
        else jedis.setex(pre.getPrefix()+key,pre.getExpiredTime(),json);
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
