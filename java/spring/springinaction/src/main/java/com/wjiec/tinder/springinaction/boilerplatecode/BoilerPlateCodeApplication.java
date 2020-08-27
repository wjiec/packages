package com.wjiec.tinder.springinaction.boilerplatecode;

import com.wjiec.tinder.springinaction.boilerplatecode.model.User;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class BoilerPlateCodeApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setLocations(new ClassPathResource("/boilerplatecode/boiler-plate-code.properties"));
        context.addBeanFactoryPostProcessor(configurer);
        context.registerBean(PropertySourcesPlaceholderConfigurer.class, () -> configurer);

        context.register(DataSourceAutoConfiguration.class);
        context.register(JdbcTemplateAutoConfiguration.class);
        context.refresh();

        System.out.println(simple(context.getBean(DataSource.class)));
        System.out.println(boilerPlate(context.getBean(JdbcTemplate.class)));
        System.out.println(boilerPlateUsername(context.getBean(JdbcTemplate.class)));
        System.out.println(boilerPlateBeanRowMapper(context.getBean(JdbcTemplate.class)));
    }

    private static List<User> simple(@NonNull DataSource dataSource) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<User> users = new LinkedList<>();

        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();

            resultSet = statement.executeQuery("select * from users;");
            while (resultSet.next()) {
                users.add(User.builder()
                    .id(resultSet.getLong(1))
                    .username(resultSet.getString(2))
                    .password(resultSet.getString(3))
                    .build()
                );
            }
        } catch (SQLException ignored) {} finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ignored) {}
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ignored) {}
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ignored) {}
            }
        }

        return users;
    }

    private static List<User> boilerPlate(@NonNull JdbcTemplate jdbcTemplate) {
        return jdbcTemplate.query("select * from users;", (rs, i) -> User.builder()
            .id(rs.getLong(1))
            .username(rs.getString(2))
            .password(rs.getString(3))
            .build());
    }

    private static List<String> boilerPlateUsername(@NonNull JdbcTemplate jdbcTemplate) {
        return jdbcTemplate.queryForList("select username from users;", String.class);
    }

    private static List<User> boilerPlateBeanRowMapper(@NonNull JdbcTemplate jdbcTemplate) {
        return jdbcTemplate.query("select * from users;", new BeanPropertyRowMapper<>(User.class));
    }

}
