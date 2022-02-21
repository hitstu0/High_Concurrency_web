package com.hitsz.high_concurrency.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import com.hitsz.high_concurrency.Data.User;
import com.hitsz.high_concurrency.Data.Goods.MiaoShaInfo;
import com.hitsz.high_concurrency.Exeception.Base.ViewException;
import com.hitsz.high_concurrency.Redis.RedisCommondFactory;
import com.hitsz.high_concurrency.Redis.Commond.RedisCommond;
import com.hitsz.high_concurrency.Redis.Key.GoodsKey;
import com.hitsz.high_concurrency.Redis.Key.UserKey;
import com.hitsz.high_concurrency.Redis.RedisServer.RedisService;
import com.hitsz.high_concurrency.Result.CodeMsg;
import com.hitsz.high_concurrency.Result.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MiaoShaService {
    @Autowired
    private GoodsService goodsService;  
    @Autowired
    private RedisService rService;
    private ConcurrentHashMap<String,requestInfo> map = new ConcurrentHashMap<>(2000);   
    private AtomicBoolean cacheLock = new AtomicBoolean(false);

    public Result<MiaoShaInfo> getMiaoShaInfo(int id) throws Exception{
        while(cacheLock.get() == true) 
            Thread.yield();
        RedisCommond get = RedisCommondFactory.getRedisGet(GoodsKey.MiaoShaInfo,id + "",MiaoShaInfo.class);
        Result<?> result = rService.doRedisCommond(get);
        //如果缓存中没有就从数据库中读取放入缓存
        if(result == null) {
            MiaoShaInfo info = null;
            //设置锁，让同一时间只能有一个请求进入数据库查询缓存
            if(cacheLock.compareAndSet(false, true)) {
               info = goodsService.getMiaoshaInfo(id);             
               RedisCommond set = RedisCommondFactory.getRedisSet(GoodsKey.MiaoShaInfo,id + "",info);
               rService.doRedisCommond(set);
               cacheLock.set(false);
               return Result.success(info);
            } else {
                while(cacheLock.get() == true) 
                   Thread.yield();
            }
        }
        result = rService.doRedisCommond(get);
        return (Result<MiaoShaInfo>) result;


    }

    public Result<Long> getMiaoShaDownCount(int id) throws Exception {
        Result<MiaoShaInfo> result = getMiaoShaInfo(id);
        MiaoShaInfo info = result.getData();
        long startTime = info.getBeginTime().getTime();
        long currentTime = System.currentTimeMillis();
        if(startTime <= currentTime) return Result.success(0l);
        return Result.success(startTime - currentTime);
    }

    public Boolean judgeUserRequestNum(User user,int goodsId) throws Exception {
        if(user == null) throw new ViewException(CodeMsg.NOT_LOGIN);
        String key = user.getId() + " " + goodsId;
        requestInfo num = map.get(key);      
        if(num == null || (System.currentTimeMillis() - num.time) > 50) {
            map.put(key,new requestInfo(System.currentTimeMillis(),1));
            return true;
        }
        if(num.requestNum > 5) return false;
        num.requestNum ++ ;
        return true;
    }

    class requestInfo {
        long time;
        int requestNum;
        public requestInfo(long time,int request) {
           this.time = time;
           this.requestNum = request;
        }
    }
}
