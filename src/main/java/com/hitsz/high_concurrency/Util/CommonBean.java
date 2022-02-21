package com.hitsz.high_concurrency.Util;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component
public class CommonBean {

    @Bean 
    public Executor getExecutor() {
        Executor pool = Executors.newFixedThreadPool(FinalValue.coreNum);
        return pool;
    }
}
