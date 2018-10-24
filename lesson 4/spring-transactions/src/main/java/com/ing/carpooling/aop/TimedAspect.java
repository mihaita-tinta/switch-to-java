package com.ing.carpooling.aop;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimedAspect {
    Logger log = LoggerFactory.getLogger(TimedAspect.class);

    public TimedAspect() {
        int i = 0;
        i++;
    }
    @Around("@annotation(com.ing.carpooling.aop.Timed)")
    public Object timed(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - start;

        log.info("timed - " + joinPoint.getSignature() + " executed in " + executionTime + "ms");
        return proceed;
    }
}
