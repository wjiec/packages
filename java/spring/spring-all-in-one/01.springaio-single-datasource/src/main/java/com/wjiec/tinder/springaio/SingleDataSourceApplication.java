package com.wjiec.tinder.springaio;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;

@Slf4j
@SpringBootApplication
public class SingleDataSourceApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SingleDataSourceApplication.class, args);
    }

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public SingleDataSourceApplication(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info(dataSource.toString());

        Connection connection = dataSource.getConnection();
        log.info(connection.toString());
        connection.close();

        jdbcTemplate.queryForList("select * from spring_user")
            .forEach((r) -> log.info(r.toString()));
    }

}
