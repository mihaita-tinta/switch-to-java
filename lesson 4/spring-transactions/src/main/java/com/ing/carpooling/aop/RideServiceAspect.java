package com.ing.carpooling.aop;

import com.ing.carpooling.domain.Ride;
import com.ing.carpooling.domain.RideRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RideServiceAspect {
    private static final Logger log = LoggerFactory.getLogger(RideServiceAspect.class);

    @Pointcut("execution(* com.ing.carpooling.service.RideService.*(..))")
    public void rideServiceAllMethods() {
    }

    @Pointcut("execution(com.ing.carpooling.domain.RideRequest *(..))")
    public void returnRideRequestMethod() {
    }

    @Around("rideServiceAllMethods() && returnRideRequestMethod()")
    public Object aroundRideRequest(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("aroundRideRequest - method: {}", proceedingJoinPoint.getSignature().getName());
        return proceedingJoinPoint.proceed();
    }

    @Before("rideServiceAllMethods() && returnRideRequestMethod() &&" +
            "args(request,..)")
    public void beforeRideRequest(RideRequest request) {
        log.info("beforeRideRequest - rideRequest: {}", request);
    }

    @AfterReturning(
            pointcut="rideServiceAllMethods() && returnRideRequestMethod()",
            returning="request")
    public void afterRequestAudit(RideRequest request) throws Throwable {
        log.info("afterRequestAudit - rideRequest: {}", request);
    }
}
