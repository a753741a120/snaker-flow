package com.snaker.framework.config.aop;

import com.snaker.framework.config.annotation.MethodLog;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @基本功能: 切面      后期添加用户时再进行用户拦截实时监控用户请求情况
 * @program:demo
 * @author:pm
 * @create:2021-06-23 10:16:24
 **/
@Slf4j
@Aspect
@Component
public class LogAdvice {

    @Pointcut("@annotation(com.snaker.framework.config.annotation.MethodLog)")
    public void annotationPointCut() {
    }

    ;

    /**
     * 前置通知
     */
    @Before("execution(public * com.snaker.*.*.controller.*.*(..))")
    public void before(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        //获取在方法上的MethodLog注解
        MethodLog annotation = method.getAnnotation(MethodLog.class);
        log.info("==============发起请求：==============");
        log.info("方法接口为:" + annotation.desc() + "*******************" + "请求模块为:" + annotation.master());
    }

    /**
     * 后置通知
     */
    @AfterReturning(value = "execution(public * com.snaker.*.*.controller.*.*(..))", returning = "returnVal")
    public void afterReturning(Object returnVal) {
        log.info("==============请求成功  请求结果为:==============");
        log.info(String.valueOf(returnVal));
    }

    /**
     * 环绕通知
     *
     * @return obj
     */
    @SneakyThrows
    @Around("execution(public * com.snaker.*.*.controller.*.*(..))")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) {
        Object obj = proceedingJoinPoint.proceed();
        return obj;
    }

    /**
     * 抛出异常通知
     *
     * @param e e
     */
    @AfterThrowing(value = "execution(public * com.snaker.*.*.controller.*.*(..))", throwing = "e")
    public void afterThrowable(Throwable e) {
        log.error("请求中出现异常:" + e);
    }

    /**
     * 最终通知
     */
    @After("execution(public * com.snaker.*.*.controller.*.*(..))")
    public void after(JoinPoint joinPoint) {
        log.info("****************************************************************************************************************************************");
    }
}