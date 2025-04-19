package com.shashankpk.BugBridgeApi.Aspect;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // Pointcut to match all methods in all classes within specific packages
    @Pointcut("within(com.example.service..*) || within(com.example.controller..*)")
    public void applicationPackagePointcut() {}

    // Pointcut to match all public methods
    @Pointcut("execution(public * *(..))")
    public void publicMethodPointcut() {}

    // Combine pointcuts: log execution of all public methods within the specified packages
    @Before("applicationPackagePointcut() && publicMethodPointcut()")
    public void logMethodEntry(JoinPoint joinPoint) {
        logger.debug("Entering method: {} in class: {}",
                     joinPoint.getSignature().getName(),
                     joinPoint.getTarget().getClass().getSimpleName());
        logger.debug("Arguments: {}", Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "applicationPackagePointcut() && publicMethodPointcut()", returning = "result")
    public void logMethodExit(JoinPoint joinPoint, Object result) {
        logger.debug("Exiting method: {} in class: {}",
                     joinPoint.getSignature().getName(),
                     joinPoint.getTarget().getClass().getSimpleName());
        logger.debug("Return value: {}", result);
    }

    @AfterThrowing(pointcut = "applicationPackagePointcut() && publicMethodPointcut()", throwing = "e")
    public void logException(JoinPoint joinPoint, Throwable e) {
        logger.error("Exception in method: {} in class: {}",
                     joinPoint.getSignature().getName(),
                     joinPoint.getTarget().getClass().getSimpleName(), e);
    }

    // Example of @Around advice for more detailed logging and timing
    @Around("applicationPackagePointcut() && publicMethodPointcut()")
    public Object logMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object proceed = joinPoint.proceed(); 
        long executionTime = System.currentTimeMillis() - startTime;
        logger.info("Method: {} in class: {} executed in {} ms",
                    joinPoint.getSignature().getName(),
                    joinPoint.getTarget().getClass().getSimpleName(),
                    executionTime);
        return proceed;
    }
}
