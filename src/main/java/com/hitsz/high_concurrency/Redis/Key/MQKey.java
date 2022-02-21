package com.hitsz.high_concurrency.Redis.Key;

import com.hitsz.high_concurrency.Redis.prefix.abstractPrefix;

public class MQKey extends abstractPrefix{

    protected MQKey(String pre, int expireTime) {
        super(pre, expireTime);
        //TODO Auto-generated constructor stub
    }
    public static MQKey failMsg = new MQKey("failMsg", 60*24*2);
    public static MQKey examOrder = new MQKey("examOrder",60*24*2);
    public static MQKey msgId = new MQKey("msgId", 60*24*2);
}
