package com.wjiec.tinder.springinaction.moreaop.audience;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Service;

@Aspect
@Service
public class Audience {

    @Pointcut("execution(* com.wjiec.tinder.springinaction.moreaop.performance.Performance.perform(..))")
    public void performance() {}

    @Pointcut("execution(* com.wjiec.tinder.springinaction.moreaop.performance.Performance.perform(..)) && bean(movie)")
    public void performanceMovie() {}

    @Pointcut("execution(* com.wjiec.tinder.springinaction.moreaop.performance.Performance.perform()) && bean(concert)")
    public void performanceConcert() {}

    @Before("performance()")
    public void silenceMobile() {
        System.out.println(">> Silence mobile");
    }

    @Before("performance()")
    public void takSeats() {
        System.out.println(">> Taking seats");
    }

    @Before("performanceMovie()")
    public void wearGlasses() {
        System.out.println(">> Wear glasses");
    }

    @AfterReturning("performance()")
    public void applause() {
        System.out.println(">> CLAP CLAP CLAP!!!");
    }

    @AfterThrowing("performance()")
    public void demandRefund() {
        System.out.println(">> Demanding a refund");
    }

    @After("performance()")
    public void goHome() {
        System.out.println(">> Go home");
    }

    @Around("performanceConcert()")
    public void watchPerformance(ProceedingJoinPoint point) {
        try {
            System.out.println("-- Around start");
            point.proceed();
            System.out.println("-- Around success");
        } catch (Throwable e) {
            e.printStackTrace();
            System.out.println("-- Around failure");
        }

        System.out.println("-- Around end");
    }

}
