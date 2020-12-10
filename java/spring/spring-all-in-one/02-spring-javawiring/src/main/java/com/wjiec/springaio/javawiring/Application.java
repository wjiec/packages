package com.wjiec.springaio.javawiring;

import com.wjiec.springaio.javawiring.model.Address;
import com.wjiec.springaio.javawiring.model.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.concurrent.ThreadLocalRandom;

@ComponentScan
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(Application.class);
        context.refresh();

        System.out.println(context.getBean(Address.class));
        System.out.println(context.getBean(User.class));
        System.out.println(context.getBean("jayson"));
    }

}
