package com.wjiec.springaio.javawiring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.concurrent.ThreadLocalRandom;

@ComponentScan
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(Application.class);
        context.refresh();

        System.out.println(context.getBean(ThreadLocalRandom.class));
        System.out.println(context.getBean("tempDir"));
    }

}
