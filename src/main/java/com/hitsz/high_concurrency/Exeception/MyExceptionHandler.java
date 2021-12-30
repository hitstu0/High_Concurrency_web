package com.hitsz.high_concurrency.Exeception;

import com.hitsz.high_concurrency.Exeception.Base.ViewException;
import com.hitsz.high_concurrency.Result.CodeMsg;
import com.hitsz.high_concurrency.Result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(value = ViewException.class)
    public Result<CodeMsg> viewExceptionHandler(ViewException e) {
        return Result.error(e.getCodeMsg());
        
    }
}
