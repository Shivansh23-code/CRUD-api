package com.review.crud.aspect;


import com.review.crud.annotation.ProfileExecution;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ProfilingAspect {

    @Around("@annotation(profileExecution)")
    public Object profileMethod(ProceedingJoinPoint joinPoint, ProfileExecution profileExecution) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        long executionTime = endTime - startTime;


        System.out.println("Method: " + joinPoint.getSignature());
        System.out.println("Execution time: " + executionTime);

        return result;
    }

}