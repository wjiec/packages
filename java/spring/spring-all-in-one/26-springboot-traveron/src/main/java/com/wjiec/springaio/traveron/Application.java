package com.wjiec.springaio.traveron;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;

import java.net.URI;

@Configuration
@SpringBootApplication
public class Application {

    @Bean
    public Traverson traverson() {
        return new Traverson(URI.create("http://localhost:8080/data-rest"), MediaTypes.HAL_JSON);
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class)
            .web(WebApplicationType.NONE)
            .run(args);
    }

}
