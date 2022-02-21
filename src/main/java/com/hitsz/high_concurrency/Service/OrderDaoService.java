package com.hitsz.high_concurrency.Service;

import javax.annotation.Resource;

import com.hitsz.high_concurrency.Data.Goods.MiaoShaInfo;
import com.hitsz.high_concurrency.Data.Order.OrderInfo;
import com.hitsz.high_concurrency.Exeception.Base.ViewException;
import com.hitsz.high_concurrency.MQ.MessageUtil;
import com.hitsz.high_concurrency.MQ.Producer;
import com.hitsz.high_concurrency.Mybatis.MabatisUtil;
import com.hitsz.high_concurrency.Mybatis.Base.GoodsBase;
import com.hitsz.high_concurrency.Mybatis.Base.MiaoShaBase;
import com.hitsz.high_concurrency.Mybatis.Base.OrderBase;
import com.hitsz.high_concurrency.Redis.RedisCommondFactory;
import com.hitsz.high_concurrency.Redis.Commond.RedisCommond;
import com.hitsz.high_concurrency.Redis.Key.MQKey;
import com.hitsz.high_concurrency.Redis.RedisServer.RedisService;
import com.hitsz.high_concurrency.Result.CodeMsg;
import com.hitsz.high_concurrency.Util.MQUtil;

import org.apache.ibatis.session.SqlSession;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderDaoService {
    @Resource(name = "payOrderProducer")
    private DefaultMQProducer producer;
    @Resource(name = "payOrderConsumer")
    private DefaultMQPushConsumer consumer;
    @Autowired
    private RedisService rService;

    //设置订单信息,并进入付款流程
    public Boolean setOrder(OrderInfo info) {  
        SqlSession session = MabatisUtil.getSqlSession();
        try {
           OrderBase base = session.getMapper(OrderBase.class);     
           base.addOrder(info);
           Message message = MessageUtil.getMessage(MQUtil.PayedOrderTopic,MQUtil.PayedOrderTag, info.getId());
           message.setDelayTimeLevel(5);

           //发送检测消息
           try {
              SendResult result = producer.send(message);
              if(result.getSendStatus() != SendStatus.SEND_OK) {
                RedisCommond rpush = RedisCommondFactory.getRedisRpush(MQKey.examOrder,info.getId());
                rService.doRedisCommond(rpush);   
              }
           } catch(Exception e) {
               RedisCommond rpush = RedisCommondFactory.getRedisRpush(MQKey.examOrder,info.getId());
               try {
                rService.doRedisCommond(rpush);
            } catch (Exception e1) {
                e1.printStackTrace();
            }   
           }
           session.commit();
           return true;
        } finally {
            session.close();
        } 
    }   
    //支付订单
    @Transactional
    public void payOrder(int id) {
        SqlSession session = MabatisUtil.getSqlSession();
        try {
           OrderBase base = session.getMapper(OrderBase.class);
           //支付并扣减库存，通过事务
           int col = base.payOrder(id);
           if(col == 0) throw new ViewException(CodeMsg.ORDER_NOT_EXITS);
        } finally {
            session.close();
        }
    } 
    //检查订单是否超时未支付,如果未支付则删除
    public int examOrder(int id) {
        int col = 0;
        SqlSession session = MabatisUtil.getSqlSession();
        try {
           OrderBase base = session.getMapper(OrderBase.class);
           col = base.examOrder(id);
           return col;
        } finally {
            session.close();
        }
    }
}
