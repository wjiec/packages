package com.wjiec.tinder.springinaction.profile;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import javax.sql.DataSource;

@ComponentScan
public class AnnotationProfileApplication {

    public static void main(String[] args) {
        activeProfiles("dev");
        System.out.println();

        activeProfiles("qa");
        System.out.println();

        activeProfiles("prod");
    }

    private static void activeProfiles(String... profiles) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        context.getEnvironment().setActiveProfiles(profiles);
        context.register(AnnotationProfileApplication.class);
        context.refresh();

        System.out.println(context.getBean(DataSource.class));
    }

}
