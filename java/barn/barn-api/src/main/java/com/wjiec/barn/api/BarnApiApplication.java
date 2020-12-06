package com.wjiec.barn.api;

import com.wjiec.barn.core.CoreConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(CoreConfiguration.class)
public class BarnApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BarnApiApplication.class, args);
    }

}
