package com.hitsz.high_concurrency.Redis.RedisServer;

import com.hitsz.high_concurrency.Redis.Commond.RedisCommond;

import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

@Service
public class RedisRecoverService extends AbstractRedisService {
    @Override
    void beforeGetResource(RedisCommond redisCommond) {
      
    }

    @Override
    void beforeExcute(RedisCommond redisCommond, Jedis jedis) throws Exception {
        // TODO Auto-generated method stub
        
    }

    @Override
    void afterExcute(RedisCommond redisCommond, Jedis jedis) {
        // TODO Auto-generated method stub
        
    }
}
