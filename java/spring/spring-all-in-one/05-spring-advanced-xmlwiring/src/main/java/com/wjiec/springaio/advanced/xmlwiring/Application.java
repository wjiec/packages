package com.wjiec.springaio.advanced.xmlwiring;

import com.wjiec.springaio.advanced.xmlwiring.database.Database;
import com.wjiec.springaio.advanced.xmlwiring.model.Notepad;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

public class Application {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/beans.xml");
        context.getEnvironment().setActiveProfiles("prod");
        context.refresh();

        System.out.println(Arrays.toString(context.getEnvironment().getActiveProfiles()));

        System.out.println(context.getBean(Notepad.class));
        System.out.println(context.getBean(Notepad.class));
        System.out.println(context.getBean(Notepad.class));

        System.out.println(context.getBean(Database.class));
        System.out.println(context.getBean("mysql"));
    }

}
