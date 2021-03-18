package com.wjiec.springaio.cloud.config.userservice;

import com.wjiec.springaio.cloud.config.userservice.config.ServiceProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties(ServiceProperties.class)
public class ConfigUserServiceApplication {

    // /actuator/refresh    =>  returns 500
    public static void main(String[] args) {
        SpringApplication.run(ConfigUserServiceApplication.class, args);
    }

}
