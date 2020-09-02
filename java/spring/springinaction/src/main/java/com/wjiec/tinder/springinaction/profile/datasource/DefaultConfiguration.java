package com.wjiec.tinder.springinaction.profile.datasource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DefaultConfiguration {

    @Bean
    public DataSource dataSource() {
        return null;
    }

}
