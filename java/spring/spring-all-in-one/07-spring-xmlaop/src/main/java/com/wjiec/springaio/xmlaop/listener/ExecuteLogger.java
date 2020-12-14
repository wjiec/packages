package com.wjiec.springaio.xmlaop.listener;

import org.aspectj.lang.ProceedingJoinPoint;

public class ExecuteLogger {

    public void prepare() {
        System.out.println("prepare environment");
    }

    public void buildCache(String sql) {
        System.out.printf("cache result on: %s\n", sql);
    }

    public void logging() {
        System.out.println("logging query");
    }

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
