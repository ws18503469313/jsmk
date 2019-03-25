package com.itmuch.AOP;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
@Aspect
@Component
@Order(1)
public class TimeControllerAOP  {
    private static Logger logger = LoggerFactory.getLogger(TimeControllerAOP.class);
    
    public static final String EDP = "execution(* com.itmuch.*.*.controller..*.*(..))";

    public TimeControllerAOP() {
    }
    
    @Around("pointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object obj = joinPoint.proceed();
//        if (System.currentTimeMillis() - start > 100) {
	        if (logger.isDebugEnabled()) {
	            logger.debug("O.O.O.O.O...took " + (System.currentTimeMillis() - start) + "ms to process " + joinPoint.getTarget().getClass().getSimpleName() + "." + joinPoint.getSignature().getName() + "()");
	        }
//        }
        return obj;
    }
    
    @Pointcut(EDP)
    public void pointcut() {
    }

    @Before("pointcut()")
    public void logBefore() {
    }

    @After("pointcut()")
    public void logAfter() {
    }

}