package com.hitsz.high_concurrency.Redis.RedisServer;

import com.hitsz.high_concurrency.Exeception.Base.ViewException;
import com.hitsz.high_concurrency.Redis.RedisConfig;
import com.hitsz.high_concurrency.Redis.redisPoolService;
import com.hitsz.high_concurrency.Redis.Commond.RedisCommond;
import com.hitsz.high_concurrency.Result.CodeMsg;
import com.hitsz.high_concurrency.Result.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


public abstract class AbstractRedisService {
    @Autowired
    private redisPoolService redisPoolService;

    public final Result<?> doRedisCommond(RedisCommond redisCommond) throws Exception{
       //客户端只有在 isClosed = false 且拿到正确的连接池才能访问 Redis
       JedisPool jedisPool = redisPoolService.getJedisPool();
       Jedis jedis = null;
       try {
          beforeGetResource(redisCommond);
          jedis = jedisPool.getResource();   
          beforeExcute(redisCommond, jedis);
          Result<?> result = redisCommond.excute(jedis);
          afterExcute(redisCommond, jedis);
          return result;
       } catch(Exception e) {    
           throw e;
       }  finally {
           if(jedis != null) jedis.close();
       }
       
    } 
    
    abstract void beforeGetResource(RedisCommond redisCommond);
    abstract void beforeExcute(RedisCommond redisCommond,Jedis jedis) throws Exception;
    abstract void afterExcute(RedisCommond redisCommond,Jedis jedis);
}
