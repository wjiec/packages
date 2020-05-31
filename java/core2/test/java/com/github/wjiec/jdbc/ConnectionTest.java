package com.github.wjiec.jdbc;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Arrays;

public class ConnectionTest {

    public static void main(String[] args) throws SQLException {
        Connection connection = Connections.get();
        System.out.println(connection);
        System.out.println(connection.getClientInfo());
        DatabaseMetaData metadata = connection.getMetaData();

        Arrays.stream(metadata.getClass().getMethods()).forEach((m) -> {
            try {
                System.out.printf("%s: %s\n", m.getName(), m.invoke(metadata));
            } catch (Exception ignored) {
                System.out.printf("%s: NEED PARAMETERS\n", m.getName());
            }
        });

        try (Statement statement = connection.createStatement()) {
            statement.execute("create table tablename (name varchar(255), age int);");
            statement.execute("insert into tablename values('a', 1), ('b', 2), ('c', 3);");

            try (ResultSet result = statement.executeQuery("select * from tablename;")) {
                while (result.next()) {
                    System.out.println(result.getString(1));
                    System.out.println(result.getInt(2));
                }
            }

            System.out.println(statement.executeUpdate("drop table tablename;"));
        }

    }

}
