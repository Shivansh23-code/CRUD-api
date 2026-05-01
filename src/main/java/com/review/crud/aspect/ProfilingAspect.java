package com.review.crud.aspect;


import com.review.crud.annotation.ProfileExecution;
import com.review.crud.config.ProfilingConfig;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ProfilingAspect {

    private final ProfilingConfig config;

    @Autowired
    public ProfilingAspect(ProfilingConfig config){
        this.config=config;
    }

    @Around("@annotation(profileExecution)")
    public Object profileMethod(ProceedingJoinPoint joinPoint, ProfileExecution profileExecution) throws Throwable {

        if(!config.isEnabled()){
            return joinPoint.proceed();
        }



        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        long executionTime = endTime - startTime;


//        Dev. Behaviour
        if("DEBUG".equalsIgnoreCase(config.getLoglevel())){
            System.out.println("Development Profiling");
            System.out.println("Method: " + joinPoint.getSignature());
            System.out.println("Execution time: " + executionTime + "ms");

        }
//        Prod. Behaviour
        else{
            System.out.println("Production Profiling");

            if(executionTime > 500){
                System.out.println("Prod. Alert: Slow Method -> "
                + joinPoint.getSignature()
                + " | Time: " + executionTime + "ms");
            }else{
                System.out.println("Method: " + joinPoint.getSignature());
                System.out.println("Execution time: " + executionTime + "ms");
            }
        }
        return result;
    }

}