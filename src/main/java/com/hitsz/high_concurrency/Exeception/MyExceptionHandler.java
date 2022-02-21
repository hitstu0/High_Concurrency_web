package com.hitsz.high_concurrency.Exeception;

import java.net.SocketException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import com.hitsz.high_concurrency.Exeception.Base.ViewException;
import com.hitsz.high_concurrency.Result.CodeMsg;
import com.hitsz.high_concurrency.Result.Result;
import com.hitsz.high_concurrency.Util.CommonMethod;

import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import redis.clients.jedis.exceptions.JedisConnectionException;

@ControllerAdvice
@Component
public class MyExceptionHandler {
    @ExceptionHandler(value = ViewException.class)
    @ResponseBody
    public Result<CodeMsg> viewExceptionHandler(ViewException e) {
        return Result.error(e.getCodeMsg());        
    }
}
