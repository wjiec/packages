package com.wjiec.tinder.springinaction.profile.datasource;

import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.h2.Driver;

import javax.sql.DataSource;

@Configuration
public class ProdConfiguration {

    @Bean
    @Primary
    @Profile("qa")
    public DataSource qaDatasource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(Driver.class);
        dataSource.setUrl("jdbc:h2:mem/qa");
        dataSource.setUsername("sa");

        return dataSource;
    }

    @Bean
    @Primary
    @Profile("prod")
    public DataSource prodDataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(Driver.class);
        dataSource.setUrl("jdbc:h2:mem/prod");
        dataSource.setUsername("sa");

        return dataSource;
    }

}
