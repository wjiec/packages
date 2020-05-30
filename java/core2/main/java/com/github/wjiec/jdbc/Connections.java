package com.github.wjiec.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connections {

    public static Connection get() throws SQLException {
        Properties properties = getProperties();
        System.setProperty("user.timezone", properties.getProperty("timezone"));
        return DriverManager.getConnection(properties.getProperty("driver"),
            properties.getProperty("username"), properties.getProperty("password"));
    }

    public static Properties getProperties() {
        Properties properties = new Properties();

        InputStream stream = Connections.class.getResourceAsStream("/jdbc/postgres.properties");
        try {
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }

}
