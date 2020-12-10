package com.wjiec.springaio.javawiring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ThreadLocalRandom;

@Configuration
public class JavaBeanConfig {

    @Bean("random")
    public ThreadLocalRandom threadLocalRandom() {
        return ThreadLocalRandom.current();
    }

    @Bean
    public String tempDir(ThreadLocalRandom random) {
        System.out.println(random);
        return "/tmp/" + random.nextInt(Integer.MAX_VALUE);
    }

}
