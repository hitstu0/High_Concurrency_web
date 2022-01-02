package com.hitsz.high_concurrency.AOP.View;

import com.hitsz.high_concurrency.Data.User;
import com.hitsz.high_concurrency.View.viewData;
import com.hitsz.high_concurrency.View.viewService;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class ViewAdvice {
    @Autowired
    private viewService vService;

    @Before("ViewPoinCut.viewCount()")
    public void viewCount(JoinPoint jPoint) {
        vService.addTotal();
    }

    @Before("ViewPoinCut.viewCurrent()")
    public void viewCurrent(JoinPoint jPoint) {
        vService.addCurrent();
    }
}
