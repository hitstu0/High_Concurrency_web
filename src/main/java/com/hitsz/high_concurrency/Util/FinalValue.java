package com.hitsz.high_concurrency.Util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class FinalValue {
    public static final String tokenName = "token";    

    public static final int TOTAL_STORE = 100;

    //线程池参数
    public static final int coreNum = 3;
    public static final int maxNum = 5;
    public static final int keepAliveTime = 1;
    public static final TimeUnit unit = TimeUnit.MINUTES;
}
