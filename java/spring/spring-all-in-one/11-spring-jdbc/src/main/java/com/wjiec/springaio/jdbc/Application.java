package com.wjiec.springaio.jdbc;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileUrlResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

@Configuration
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(Application.class);
        context.refresh();

        DataSource dataSource = context.getBean(DataSource.class);
        System.out.println(dataSource);

        initScripts(dataSource);
        queryFirst(dataSource);

        JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
        System.out.println(jdbcTemplate);

        queryFirstByJdbcTemplate(jdbcTemplate);

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = context.getBean(NamedParameterJdbcTemplate.class);
        queryAdminByNamedJdbcTemplate(namedParameterJdbcTemplate);
    }

    private static void initScripts(DataSource dataSource) {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.setContinueOnError(false);
        populator.setSeparator(";");
        populator.setSqlScriptEncoding("UTF-8");
        populator.addScript(new FileUrlResource(Application.class.getResource("/schema.sql")));
        populator.addScript(new FileUrlResource(Application.class.getResource("/data.sql")));

        DatabasePopulatorUtils.execute(populator, dataSource);
    }

    private static void queryFirst(DataSource dataSource) {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            connection = dataSource.getConnection();
            System.out.println(connection);

            statement = connection.createStatement();
            rs = statement.executeQuery("select * from administrator limit 1;");
            System.out.println(rs);

            while (rs.next()) {
                System.out.println("Id: " + rs.getLong(1));
                System.out.println("Username: " + rs.getString(2));
                System.out.println("Password: " + rs.getString(3));
                System.out.println("CreatedAt: " + rs.getDate(4));
                System.out.println("UpdatedAt: " + rs.getDate(5));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
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
    }

    private static void queryFirstByJdbcTemplate(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.query("select * from administrator limit 1;", (rs) -> {
            System.out.println("queryFirstByJdbcTemplate: Rs = " + rs);

            int ok = jdbcTemplate.update("update administrator set username = ? where `id` = ?", "admin_rename",
                rs.getLong(1));
            System.out.println("queryFirstByJdbcTemplate: Ok = " + ok);
        });
    }

    private static void queryAdminByNamedJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        jdbcTemplate.query("select * from administrator where username = :username", new HashMap<String, Object>() {{
            put("username", "admin_rename");
        }}, (rs) -> {
            System.out.println(rs);
            System.out.println(rs.getLong(1));
            System.out.println(rs.getString(2));
            System.out.println(rs.getString(3));
        });
    }

    @Bean
    public DataSource dataSource() {
        DataSourceProperties properties = new DataSourceProperties();
        properties.setDriverClassName("org.h2.Driver");
        properties.setUrl("jdbc:h2:mem:springaio");
        properties.setUsername("sa");
        properties.setSqlScriptEncoding(StandardCharsets.UTF_8);

        DataSourceBuilder<?> builder = properties.initializeDataSourceBuilder();

        return builder.build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource());
    }

}
