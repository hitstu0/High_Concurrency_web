package com.hitsz.high_concurrency.Validator;

import java.lang.annotation.*;

//当子类继承父类方法时注解可被继承
@Inherited
//注解可以修饰的目标
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.PARAMETER})
//注解的生命周期
@Retention(RetentionPolicy.RUNTIME)
public @interface UserIdentify {
    
}
