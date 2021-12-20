package com.hitsz.high_concurrency.AOP;

import org.aspectj.lang.annotation.Pointcut;

public class CommonPoinCut {
    @Pointcut("@annotation(com.hitsz.high_concurrency.Validator.UserIdentify)")
    public void identifyUser(){}
}
