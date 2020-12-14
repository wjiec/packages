package com.wjiec.springaio.javaaop.listener;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecuteLogger {

    @Pointcut("execution(* com.wjiec.springaio.javaaop.database.Database.execute(..))")
    public void anyExecute() {}

    @Pointcut("execution(* com.wjiec.springaio.javaaop.database.Database.execute(String, ..)) && args(sql)")
    public void executeSql(String sql) {}

    @Before("execution(* com.wjiec.springaio.javaaop.database.Database.execute(..))")
    public void prepare() {
        System.out.println("prepare environment");
    }

    @AfterReturning(value = "executeSql(sql)", argNames = "sql")
    public void buildCache(String sql) {
        System.out.printf("cache result on: %s\n", sql);
    }

    @After("anyExecute()")
    public void logging() {
        System.out.println("logging query");
    }

    @Around("anyExecute() && bean(mysql)")
    public Object mysqlDumper(ProceedingJoinPoint point) {
        try {
            System.out.println("-- mysql before");
            Object result = point.proceed();
            System.out.println("-- mysql success");

            return result;
        } catch (Throwable e) {
            System.out.printf("-- mysql error: %s\n", e.getMessage());
        } finally {
            System.out.println("-- mysql end");
        }

        return null;
    }

}
