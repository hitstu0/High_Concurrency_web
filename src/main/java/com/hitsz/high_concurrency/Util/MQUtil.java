package com.hitsz.high_concurrency.Util;

import java.sql.Time;

public class MQUtil {
    //RocketMQ
    private static boolean get = false;
    private static boolean gb = false;
    public static final String MiaoShaOrderTopic = getRandomTopic("mi");
    public static final String NameServerAdr = "10.250.169.7:9876";
    
    public static final String createOrderTag = "creatOrder";
    public static final String produceOrderGroup = "porder";
    public static final String consumerOrderGroup = "corder";

    public static final String PayedOrderTopic = getRB();
    public static final String PayedOrderTag = "payedOrder1";
    public static final String PayedOrderGroup = "payedGroup";
    public static final String exaOrderGroup = "exaOrder";


    private static String getRandomTopic(String topic) {
        if(get) 
           return MiaoShaOrderTopic;
        else {
            get = true;
            return topic + System.currentTimeMillis();
        }         
    }

    private static String getRB() {
       if(gb) {
           return PayedOrderTopic;
       } else {
           gb = true;
           return System.currentTimeMillis() + "";
       }
    }
}
