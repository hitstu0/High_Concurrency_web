package com.hitsz.high_concurrency.Redis.Commond;

import com.hitsz.high_concurrency.Redis.Key.GoodsKey;
import com.hitsz.high_concurrency.Redis.Key.OrderKey;
import com.hitsz.high_concurrency.Redis.LUA.Store;
import com.hitsz.high_concurrency.Result.Result;

import redis.clients.jedis.Jedis;

public class RedisDecrStore extends RedisCommond {
    
    private int goodsId;
    private int userId;
    public RedisDecrStore(int goodsId,int userId) {
       this.goodsId = goodsId;
       this.userId = userId;
    }

    @Override
    Result<?> doCommond(Jedis jedis) {
        int i = (int)(long)jedis.evalsha(Store.getDecrCode(),2,GoodsKey.MiaoShaGoods.getPrefix()+goodsId,OrderKey.WaterUser.getPrefix()+userId);
        return Result.success(i);
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return 0;
    }
    
    
}
