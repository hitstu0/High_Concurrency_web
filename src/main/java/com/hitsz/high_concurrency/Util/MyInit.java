package com.hitsz.high_concurrency.Util;

import java.util.concurrent.Executor;

import com.hitsz.high_concurrency.Data.Goods.MiaoShaInfo;
import com.hitsz.high_concurrency.Ping.RedisObserver;
import com.hitsz.high_concurrency.Ping.heartBeatPing;
import com.hitsz.high_concurrency.Ping.Interfaces.Subject;
import com.hitsz.high_concurrency.Redis.RedisCommondFactory;
import com.hitsz.high_concurrency.Redis.RedisConfig;
import com.hitsz.high_concurrency.Redis.Commond.RedisCommond;
import com.hitsz.high_concurrency.Redis.Key.GoodsKey;
import com.hitsz.high_concurrency.Redis.LUA.Store;
import com.hitsz.high_concurrency.Redis.RedisServer.RedisService;
import com.hitsz.high_concurrency.Result.Result;
import com.hitsz.high_concurrency.Service.GoodsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyInit implements CommandLineRunner{
    @Autowired
    private RedisService rService;
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private RedisObserver redisObserver;
    @Autowired
    private RedisConfig redisConfig;
    @Autowired
    private Executor executor;

    @Override
    public void run(String... args) throws Exception {
        RedisCommond flushDB = RedisCommondFactory.getRedisFlushDB();
        RedisCommond set = RedisCommondFactory.getRedisSet(GoodsKey.MiaoShaGoods,1+"",100);
        RedisCommond loadLUA = RedisCommondFactory.getRedisLoadLUA(Store.decrStoreInMiaoSha);         
        rService.doRedisCommond(flushDB);
        Result<?> result = rService.doRedisCommond(loadLUA);        
        // 缓存 LUA 脚本
        String code = (String)result.getData();
        Store.setDecrCode(code);
        // 预加载库存
        rService.doRedisCommond(set);
        
        // 预加载秒杀信息
        MiaoShaInfo info = goodsService.getMiaoshaInfo(1);
        RedisCommond setInfo = RedisCommondFactory.getRedisSet(GoodsKey.MiaoShaInfo,1+"",info);
        rService.doRedisCommond(setInfo);

        //初始化Ping 程序
        heartBeatPing ping = new heartBeatPing(redisConfig.getHost(),redisConfig.getPort() + "");
        ping.addObserver(redisObserver);
        executor.execute(ping);
    }    

    
}
