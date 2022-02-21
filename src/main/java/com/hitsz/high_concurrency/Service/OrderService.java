package com.hitsz.high_concurrency.Service;
import com.hitsz.high_concurrency.Data.Order.OrderInfo;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import com.hitsz.high_concurrency.Data.User;
import com.hitsz.high_concurrency.Data.Goods.MiaoShaInfo;
import com.hitsz.high_concurrency.Exeception.Base.ViewException;
import com.hitsz.high_concurrency.MQ.Consumer;
import com.hitsz.high_concurrency.MQ.MessageUtil;
import com.hitsz.high_concurrency.MQ.Producer;
import com.hitsz.high_concurrency.Redis.RedisCommondFactory;
import com.hitsz.high_concurrency.Redis.Commond.RedisCommond;
import com.hitsz.high_concurrency.Redis.Key.GoodsKey;
import com.hitsz.high_concurrency.Redis.Key.MQKey;
import com.hitsz.high_concurrency.Redis.RedisServer.RedisService;
import com.hitsz.high_concurrency.Result.CodeMsg;
import com.hitsz.high_concurrency.Result.Result;
import com.hitsz.high_concurrency.Util.MD5Util;
import com.hitsz.high_concurrency.Util.MQUtil;

import org.apache.ibatis.javassist.bytecode.stackmap.BasicBlock.Catch;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderService {   
    @Autowired
    private OrderDaoService oDaoService;
    @Autowired   
    private RedisService rService;
    private ConcurrentHashMap<Integer,Boolean> storeMap = new ConcurrentHashMap<>();
    @Autowired
    private MiaoShaService miaoShaService;
    //通过名字注入
    @Resource(name = "miaoShaProducer")
    private DefaultMQProducer producer;
    @Resource(name = "miaoShaConsumer")
    private DefaultMQPushConsumer[] consumer;
    

    @Autowired
    private RedoService redoService;


    private ConcurrentHashMap<String,String> map = new ConcurrentHashMap<>();

    public CodeMsg setOrder(User user,OrderInfo info) throws Exception {
       if(user == null) throw new ViewException(CodeMsg.NOT_LOGIN);              
       int userId = user.getId();
       int goodsId = info.getGoodsId();
       //判断活动是否正在进行中,数据提前放入Redis
       Result<MiaoShaInfo> resul = miaoShaService.getMiaoShaInfo(info.getGoodsId());
       MiaoShaInfo in = resul.getData();      
       if( in.getBeginTime().getTime() > System.currentTimeMillis() )
         throw new ViewException(CodeMsg.MIAOSHA_NOT_START);

       //内存标记，如果卖完就不访问 Redis
       if(storeMap.contains(goodsId)) return CodeMsg.GOODS_SHORTAGE;
       RedisCommond decrStore = RedisCommondFactory.getRedisDecrStore(goodsId, userId);
       Result<?> c = rService.doRedisCommond(decrStore);
       int code = (int) c.getData();
       switch(code) {
           case -1 : 
           // storeMap.put(goodsId,true);
            return CodeMsg.GOODS_SHORTAGE;
           case 0 :
            return CodeMsg.USER_BUY_ALREADY;
           case 1 :           
            Message msg = MessageUtil.getMessage(MQUtil.MiaoShaOrderTopic,MQUtil.createOrderTag,info);
            //先记录消息唯一性
            RedisCommond set = RedisCommondFactory.getRedisSet(MQKey.msgId,info.getUserId() + " " + info.getGoodsId(), 1);
            rService.doRedisCommond(set);

            try {
               SendResult result = null;         
               result = producer.send(msg);                  
               if(result.getSendStatus() != SendStatus.SEND_OK) {
                  redoService.redoMessgae(msg);
               }
            } catch (Exception e) {  
               redoService.redoMessgae(msg);                           
            }
       }
       return CodeMsg.SUCCESS;
    }  
    
    //支付订单，如果订单过期会由其他程序删除
    public CodeMsg payOrder(int id) {
         oDaoService.payOrder(id);
         return CodeMsg.SUCCESS;
    }
    

    //获取动态 URL
    public String getPath(int id,User user) throws Exception {
         String path = id + "yecheng";
         map.put(user.getId() + " " + id,path);
         return path;
    }

    //验证动态 URL
    public boolean judgePath(int id,User user,String path) {
       String p = map.get(user.getId() + " " + id);
       if(p == null || !p.equals(path)) return false;
       return true;
    }
}
