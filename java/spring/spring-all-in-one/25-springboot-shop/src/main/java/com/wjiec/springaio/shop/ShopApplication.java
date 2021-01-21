package com.wjiec.springaio.shop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import java.util.Arrays;

@Slf4j
@SpringBootApplication
public class ShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
    }

    @Bean
    @Profile("dev")
    public CommandLineRunner environmentDumper(ApplicationContext context) {
        return (args) -> {
            log.info(Arrays.toString(args));
            log.info(context.getEnvironment().toString());
        };
    }

    @Bean
    @Profile("!dev")
    public CommandLineRunner alerter(Environment environment) {
        return (args) -> log.info(Arrays.toString(environment.getActiveProfiles()));
    }

}
