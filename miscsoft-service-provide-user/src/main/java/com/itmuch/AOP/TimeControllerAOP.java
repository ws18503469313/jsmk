package com.itmuch.AOP;

import com.cloud.model.User;
import com.itmuch.util.MyWebUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
@Aspect
@Component
@Order(1)
public class TimeControllerAOP  {
    private static Logger logger = LoggerFactory.getLogger(TimeControllerAOP.class);
    
    public static final String EDP = "execution(public * com.itmuch.controller..*.*(..))";

    public TimeControllerAOP() {

    }

    /**
     * around 是在整个任务嵌入在around之中
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("pointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        User user = (User)MyWebUtils.getCurrentRequest().getSession().getAttribute("user");
        String username = user == null ? " " : "["+user.getUsername()+"]";
        Object obj = joinPoint.proceed();
        if (System.currentTimeMillis() - start > 500) {//响应时间过长的访问进行日志记录
	        if (logger.isInfoEnabled()) {
	            logger.info(username + "O.O.O.O.O...took "+(System.currentTimeMillis() - start)
                            + "ms to process " + joinPoint.getTarget().getClass().getTypeName()
                            + "." + joinPoint.getSignature().getName() + "()");
	        }
        }
        return obj;
    }
    
    @Pointcut(EDP)
    public void pointcut() {
    }

    @Before("pointcut()")
    public void logBefore() {
        //开始是再进入业务前
//        logger.info("o.o.o.O.O...pointcut---------------------------befor");
    }

    @After("pointcut()")
    public void logAfter() {
        //结束是在业务之后.
//        logger.info("o.o.o.O.O...pointcut---------------------------after");
    }

}