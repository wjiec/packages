package com.wjiec.tinder.springinaction.moreaop.encore;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.DeclareParents;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class EncoreAspect {

    @DeclareParents(value = "com.wjiec.tinder.springinaction.moreaop.performance.Performance+", defaultImpl = DefaultEncoreableImpl.class)
    public static Encoreable encoreable;

    @Pointcut("execution(* com.wjiec.tinder.springinaction.moreaop.encore.Encoreable.performEncore(String, int)) && args(name, count)")
    public void performanceEncore(String name, int count) {}

    @Before("performanceEncore(name, count)")
    public void welcome(String name, int count) {
        System.out.printf("++ encore %s %d times\n", name, count);
    }

}
