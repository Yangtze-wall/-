package com.retail.bargain.config;

import com.retail.bargain.annotation.ApiAnnotation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * ClassName AopAnnotation
 * Date 2023/3/27 18:37
 **/
@Aspect
@Component

public class AopAnnotation {
    @Around("@annotation(annotation)")
    public Object me(ProceedingJoinPoint proceedingJoinPoint, ApiAnnotation annotation) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();
        Object arg = args[0];
        System.out.println("进入参数是"+arg);
        Object process=null;
        process = proceedingJoinPoint.proceed();
        String value = annotation.value();
        System.out.println("这个方法是"+value);
        return process;
    }
}
