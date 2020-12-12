package com.wjiec.advanced.javawiring;

import com.wjiec.advanced.javawiring.person.Person;
import com.wjiec.advanced.javawiring.shopping.Item;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan
@PropertySource("/application.properties")
public class Application {

    public static void main(String[] args) {
        activateProfile("father");
        activateProfile("mother");
        activateProfile("children");
    }

    private static void activateProfile(String profile) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles(profile);
        context.register(Application.class);
        context.refresh();

        System.out.println(context.getBean(Person.class));
        for (var item : context.getBeansOfType(Item.class).values()) {
            System.out.println("\t" + item);
        }

        System.out.printf("PrimaryItem: %s\n", context.getBean(Item.class));
    }

}
