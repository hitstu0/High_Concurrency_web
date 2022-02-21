package com.hitsz.high_concurrency.Redis.Key;

import com.hitsz.high_concurrency.Redis.prefix.abstractPrefix;

public class GoodsKey extends abstractPrefix{
    private GoodsKey(String pre, int expireTime) {
        super(pre, expireTime);
        //TODO Auto-generated constructor stub
    }
    public static GoodsKey MiaoShaGoods = new GoodsKey("miaosha",60*24);
    public static GoodsKey MiaoShaInfo = new GoodsKey("miaoshaInfo", 60*24);
    public static GoodsKey MiaoShaPath = new GoodsKey("miaoshaPath", 60*24);
    public static GoodsKey MiaoShaCache = new GoodsKey("miaoshaCha", 60 * 24);
}
