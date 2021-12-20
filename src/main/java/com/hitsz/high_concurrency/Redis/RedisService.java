package com.hitsz.high_concurrency.Redis;

import com.alibaba.fastjson.JSON;
import com.hitsz.high_concurrency.Redis.Key.UserKey;
import com.hitsz.high_concurrency.Redis.prefix.prefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
@Service
public class RedisService {
    @Autowired
    private RedisConfig redisConfig;
    @Autowired 
    private JedisPool jedisPool;
    @Bean
    public JedisPool getJedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(redisConfig.getMaxIdle());
        jedisPoolConfig.setMaxTotal(redisConfig.getMaxActive());
        jedisPoolConfig.setMaxWaitMillis(redisConfig.getMaxWait());
        JedisPool jedisPool = new JedisPool(jedisPoolConfig,redisConfig.getHost(),redisConfig.getPort());
        return jedisPool;
    }

    public <T> T get(prefix pre,String key,Class<T> clazz) {
        //在 try 块中获取连接，方便连接用完后释放
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String json = jedis.get(pre.getPrefix() + key);
            //泛型返回值要进行类型强转
            if (clazz == String.class) {
                return (T) json;
            } else {
                return jsonToObject(json,clazz);
            }
        } finally {
            if(jedis != null) jedis.close();
        }
    }
    public void set(prefix pre,String key,Object value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String json;
            if(value.getClass() == String.class) json = (String) value;
            else json = ObjectToJson(value);
            if(pre.getExpiredTime() == 0) jedis.set(pre.getPrefix()+key,json);
            else jedis.setex(pre.getPrefix()+key,pre.getExpiredTime(),json);
        } finally {
            if(jedis != null) jedis.close();
        }
    }
    public static <T> T jsonToObject(String json,Class<T>clazz) {
        if(json == null) return null;
        return JSON.toJavaObject(JSON.parseObject(json),clazz);        
    }
    public static String ObjectToJson(Object o) {
        if(o == null) return null;
        return JSON.toJSONString(o);
    }
}
