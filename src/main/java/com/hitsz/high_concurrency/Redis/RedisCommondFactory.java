package com.hitsz.high_concurrency.Redis;

import com.hitsz.high_concurrency.Redis.Commond.RedisCommond;
import com.hitsz.high_concurrency.Redis.Commond.RedisDecrStore;
import com.hitsz.high_concurrency.Redis.Commond.RedisDelete;
import com.hitsz.high_concurrency.Redis.Commond.RedisFlushDB;
import com.hitsz.high_concurrency.Redis.Commond.RedisGet;
import com.hitsz.high_concurrency.Redis.Commond.RedisGetBit;
import com.hitsz.high_concurrency.Redis.Commond.RedisLoadLUA;
import com.hitsz.high_concurrency.Redis.Commond.RedisRpush;
import com.hitsz.high_concurrency.Redis.Commond.RedisSet;
import com.hitsz.high_concurrency.Redis.Commond.RedisSetBit;
import com.hitsz.high_concurrency.Redis.Key.GoodsKey;
import com.hitsz.high_concurrency.Redis.prefix.prefix;

public class RedisCommondFactory {
    
    public static RedisCommond getRedisFlushDB() {
        return new RedisFlushDB();
    }
    public static <T> RedisCommond getRedisGet(prefix pre,String key,Class<T> clazz) {
        return new RedisGet(pre, key, clazz);
    }

    public static RedisCommond getRedisRpush(prefix pre,Object value) {
        return new RedisRpush(pre, value);
    }

    public static RedisCommond getRedisLoadLUA(String lua) {
        return new RedisLoadLUA(lua);
    }

    public static RedisCommond getRedisSet(prefix pre,String key,Object value) {
        return new RedisSet(pre, key, value);
    }

    public static RedisCommond getRedisDecrStore(int goodsId,int userId) {
        return new RedisDecrStore(goodsId, userId);
    }

    public static RedisCommond getRedisDelete(prefix pre,String key) {
        return new RedisDelete(pre, key);
    }

    public static RedisCommond getRedisSetBit(GoodsKey miaoShaCache,int index,boolean value) {
         return new RedisSetBit(miaoShaCache, index, value);
    }

    public static RedisCommond getRedisGetBit(GoodsKey miaoShaCache,int index) {
        return new RedisGetBit(miaoShaCache, index);
    }
}
