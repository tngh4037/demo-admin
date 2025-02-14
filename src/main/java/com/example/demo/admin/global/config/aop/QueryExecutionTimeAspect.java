package com.example.demo.admin.global.config.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class QueryExecutionTimeAspect {

    private static final int QUERY_SLOW_THRESHOLD_MS = 5_000;

    @Around("execution(* com.example.demo.admin.domain.*.repository.*Repository.*(..))")
    public Object joinProceed(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        long elapsedTime = endTime - startTime;
        if (elapsedTime >= QUERY_SLOW_THRESHOLD_MS) {
            String className = joinPoint.getTarget().getClass().getSimpleName(); // class name
            String methodName = joinPoint.getSignature().getName(); // method name

            log.info("[{}.{}] executed in {} ms", className, methodName, elapsedTime);
        }

        return result;
    }
}
