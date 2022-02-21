package com.hitsz.high_concurrency.MQ;

import com.hitsz.high_concurrency.Data.Order.Order;
import com.hitsz.high_concurrency.Data.Order.OrderInfo;
import com.hitsz.high_concurrency.Redis.Commond.RedisCommond;
import com.hitsz.high_concurrency.Redis.RedisServer.RedisService;
import com.hitsz.high_concurrency.Util.MQUtil;

import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class MessageUtil {
    public static <T> Message getMessage(String topic,String tag,T msg) {
        Message mes = new Message(topic,tag,RedisCommond.ObjectToJson(msg).getBytes());      
        return mes;
    }
}
