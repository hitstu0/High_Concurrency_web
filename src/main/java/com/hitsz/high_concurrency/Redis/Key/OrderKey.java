package com.hitsz.high_concurrency.Redis.Key;

import com.hitsz.high_concurrency.Redis.prefix.abstractPrefix;

public class OrderKey extends abstractPrefix{

    private OrderKey(String pre, int expireTime) {
        super(pre, expireTime);
        //TODO Auto-generated constructor stub
    }
    public static OrderKey WaterUser = new OrderKey("waterUser", 60*24); 
}
