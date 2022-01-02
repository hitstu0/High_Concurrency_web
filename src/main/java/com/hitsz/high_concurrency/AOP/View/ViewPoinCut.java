package com.hitsz.high_concurrency.AOP.View;

import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
public class ViewPoinCut {
    @Pointcut("@annotation(com.hitsz.high_concurrency.Validator.ViewCount)")
    public void viewCount(){}

    @Pointcut("@annotation(com.hitsz.high_concurrency.Validator.ViewCurrent)")
    public void viewCurrent(){}
}
