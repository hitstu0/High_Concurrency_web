package com.hitsz.high_concurrency.Validator;

import com.hitsz.high_concurrency.Util.CommonMethod;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class isMobileValidator implements ConstraintValidator<isMobile,String> {

    private boolean required = false; //注解的值是否是必须的
    @Override
    public void initialize(isMobile constraintAnnotation) {       
        required = constraintAnnotation.required();
    }
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(required) {
            return CommonMethod.judgeMobile(value);
        } else {
            return true;
        }

    }

}
