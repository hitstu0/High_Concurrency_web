package com.hitsz.high_concurrency.Redis.Commond;

import com.hitsz.high_concurrency.Result.Result;

import redis.clients.jedis.Jedis;

public class RedisLoadLUA extends RedisCommond {
    
    private String lua;
    public RedisLoadLUA(String lua){
        this.lua = lua;
    }

    @Override
    Result<?> doCommond(Jedis jedis) {        
        return Result.success(jedis.scriptLoad(lua));        
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return 0;
    }
}
