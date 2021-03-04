package com.wjiec.springaio.reactor.quickstart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args.length == 0 ? new String[]{"username"} : args);
    }

}
