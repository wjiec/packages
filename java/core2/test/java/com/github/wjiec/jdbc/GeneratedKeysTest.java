package com.github.wjiec.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GeneratedKeysTest {

    public static void main(String[] args) {
        try {
            Connection connection = Connections.get();
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("create table bar (id serial4 primary key, name varchar(255), age int);");

                String insertSql = "insert into bar (\"name\", \"age\") values ('jayson', 24), ('jayson', 25);";
                statement.executeUpdate(insertSql, Statement.RETURN_GENERATED_KEYS);
                try (ResultSet rs = statement.getGeneratedKeys()) {
                    while (rs.next()) {
                        System.out.println(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
