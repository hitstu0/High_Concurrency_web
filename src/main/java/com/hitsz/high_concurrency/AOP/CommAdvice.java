package com.hitsz.high_concurrency.AOP;

import com.hitsz.high_concurrency.Data.User;
import com.hitsz.high_concurrency.Service.LoginService;

import org.apache.catalina.connector.RequestFacade;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Order(0)
public class CommAdvice {
    
    @Autowired
    private LoginService loginService;
    
    @Around("CommonPoinCut.identifyUser()")
    /**由于使用around，所以方法返回值因由该方法抛出*/
    public Object identifyUser(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        int user = -1; int httpSerRequest = -1;
        for(int i = 0 ; i < args.length ; ++ i) {            
            if(args[i].getClass() == User.class) user = i;
            if(args[i].getClass() == RequestFacade.class) httpSerRequest = i;
        }       
        if(user == -1 || httpSerRequest == -1) {
            throw new IllegalArgumentException("注解添加错误：登陆验证信息缺失");
        }
        HttpServletRequest request = (RequestFacade)args[httpSerRequest];
        args[user] = loginService.getUserByRequest(request);
        try {           
            return joinPoint.proceed(args);
        } catch (Throwable e) {            
            e.printStackTrace();
        }
        return null;
    }
}
