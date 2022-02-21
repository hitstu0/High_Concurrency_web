package com.hitsz.high_concurrency.Redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Jedis;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Service
public class redisPoolService {

    
    @Autowired
    private RedisConfig redisConfig;
    
    @Autowired
    private JedisPool jedisPool;
    
    
    public void updateJedisPool() {
        jedisPool = getPool();
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }
    
    
    @Bean
    private JedisPool getPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();   
        jedisPoolConfig.setMaxIdle(redisConfig.getMaxIdle());
        jedisPoolConfig.setMaxTotal(redisConfig.getMaxActive());
        jedisPoolConfig.setMaxWaitMillis(redisConfig.getMaxWait());
        JedisPool jedisPool = new JedisPool(jedisPoolConfig,redisConfig.getHost(),redisConfig.getPort());   
        return jedisPool;
    }
}
