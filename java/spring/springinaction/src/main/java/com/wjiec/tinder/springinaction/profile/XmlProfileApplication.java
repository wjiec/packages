package com.wjiec.tinder.springinaction.profile;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;

public class XmlProfileApplication {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext();

        context.getEnvironment().setActiveProfiles("dev");
        context.setConfigLocation("classpath:profile/xml-profile.xml");
        context.refresh();

        System.out.println(context.getBean(DataSource.class));
    }

}
