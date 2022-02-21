package com.hitsz.high_concurrency.Redis.LUA;

import com.hitsz.high_concurrency.Data.Order.Order;
import com.hitsz.high_concurrency.Redis.Key.GoodsKey;
import com.hitsz.high_concurrency.Redis.Key.OrderKey;

public class Store {
    public static String decrStoreInMiaoSha = 
    "local store = redis.call('GET',KEYS[1])\n" +
    "if store == '0' then\n" +
    "return -1 end\n" +
    "local user = redis.call('EXISTS',KEYS[2])\n" +
    "if user == 1 then\n" +
    "return 0 end\n"+
    "redis.call('SET',KEYS[1],store - 1)\n"+
    "redis.call('SET',KEYS[2],0)\n"+
    "return 1\n";
    private static String DECRICODE;
    public static void setDecrCode(String s) {
        DECRICODE = s;
    }
    public static String getDecrCode() {
        return DECRICODE;
    }
}
