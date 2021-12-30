package com.hitsz.high_concurrency.AOP.View;

import org.aspectj.lang.annotation.Pointcut;

public class ViewPoinCut {
    @Pointcut("@annotation(com.hitsz.high_concurrency.Validator.ViewCount)")
    public void viewCount(){}
}
