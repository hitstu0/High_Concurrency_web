package com.hitsz.high_concurrency.Validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.ConcurrentHashMap;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewCount {
    
}
