package com.premraj.product.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAdvice {

    Logger log = LoggerFactory.getLogger(LoggingAdvice.class);

    @Pointcut(value = "execution(* com.premraj.product.controller.ProductController.searchProducts(..) )")
    public void myPointcut() {

    }

    @Around("myPointcut()")
    public Object applicationLogger(ProceedingJoinPoint pjp) throws Throwable {
        ObjectMapper mapper = new ObjectMapper();
        String methodName = pjp.getSignature().getName();
        String className = pjp.getTarget().getClass().toString();
        Object[] array = pjp.getArgs();
        log.debug("method invoked " + className + ":" + methodName + "()" + " ARGUMENTS:" + mapper.writeValueAsString(array));
        long startTime = System.nanoTime();
        Object object = pjp.proceed();
        log.info(pjp + " -> " + (System.nanoTime() - startTime) / 1000000 + " ms is RESPONSE TIME");
        log.debug(className + ":" + methodName + "() " + "RESPONSE:" + mapper.writeValueAsString(object));
        return object;
    }

}