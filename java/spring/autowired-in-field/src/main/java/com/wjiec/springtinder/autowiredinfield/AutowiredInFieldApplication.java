package com.wjiec.springtinder.autowiredinfield;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class AutowiredInFieldApplication implements CommandLineRunner {

    // Autowired 1
//    @Autowired
    Apple apple;

    // Autowired 1
//    @Autowired
    Banana banana;

    @Autowired
    ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(AutowiredInFieldApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("apple: " + apple);
        System.out.println("banana: " + banana);

        Plate plate = applicationContext.getBean(Plate.class);
        System.out.println(plate.colors());
    }

    @Component
    static class Apple {
        public String color() {
            return "red";
        }
    }

    @Component
    static class Banana {
        public String color() {
            return "yellow";
        }
    }

    @Component
    static class Plate {

        @Autowired
        private Apple apple;

        @Autowired
        private Banana banana;

        public List<String> colors() {
            System.out.println(apple);
            System.out.println(banana);
            return Arrays.asList(apple.color(), banana.color());
        }
    }

}
