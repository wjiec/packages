package com.wjiec.springaio.javawiring.config;

import com.wjiec.springaio.javawiring.model.Address;
import com.wjiec.springaio.javawiring.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaBeanConfig {

    @Bean
    public Address address() {
        return Address.builder()
            .province("192")
            .city("168")
            .district("1.1")
            .build();
    }

    @Bean("jayson")
    public User user(Address address) {
        return User.builder()
            .name("Jayson")
            .address(address)
            .build();
    }

}
