package com.hitsz.high_concurrency.Redis.Commond;
import java.util.concurrent.atomic.AtomicBoolean;

import com.alibaba.fastjson.JSON;
import com.hitsz.high_concurrency.Exeception.Base.ViewException;
import com.hitsz.high_concurrency.Result.CodeMsg;
import com.hitsz.high_concurrency.Result.Result;
import com.hitsz.high_concurrency.Util.CommonMethod;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

public abstract class RedisCommond {
    private boolean judge = true;

    public static <T> T jsonToObject(String json,Class<T>clazz) {
        if(json == null) return null;
        return JSON.toJavaObject(JSON.parseObject(json),clazz);        
    }
    public static String ObjectToJson(Object o) {
        if(o == null) return null;
        return JSON.toJSONString(o);
    }
    abstract Result<?> doCommond(Jedis jedis) throws JedisConnectionException;

    public Result<?> excute(Jedis jedis) {
        Result<?> result = doCommond(jedis);
        return result;
    }

    public void disableJudge() {
        judge = false;
    } 

    public boolean getJudge() {
        return judge;
    }
    public abstract int hashCode();
}
