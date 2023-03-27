//package com.retail.colonel.config;
//
//import lombok.extern.log4j.Log4j2;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Enumeration;
//
//
///**
// * @author Lenovo
// * aop日志打印
// */
//@Aspect
//@Component
//@Log4j2
//public class Aop {
//    /**
//     * 日志打印路径(包)
//     */
//    @Pointcut("execution(* com.retail.colonel.controller.*.*(..))")
//    public void p1() {
//    }
//
//    @Before("p1()")
//    public void aop(JoinPoint joinPoint) throws Exception {
//        //接收到请求,记录请求内容
//        RequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = ((ServletRequestAttributes) attributes).getRequest();
//
//        //记录请求内容
//        log.info("URL : " + request.getRequestURL().toString());
//        log.info("HTTP_METHOD : " + request.getMethod());
//        log.info("IP : " + request.getRemoteAddr());
//        Enumeration<String> enu = request.getParameterNames();
//        while (enu.hasMoreElements()) {
//            String s = enu.nextElement();
//            log.info("name:{},value:{}", s, request.getParameter(s));
//        }
//
//    }
//
//    @AfterReturning(returning = "ret", pointcut = "p1()")
//    public void doAfterReturning(Object ret) throws Throwable {
//        //处理完请求 , 返回内容
//        log.info("返回值:{}", ret);
//    }
//}
