package com.github.wjiec.jdbc;

import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.pbkdf2.Strings;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class ResultMetaTest {

    public static void main(String[] args) {
        try {
            Connection connection = Connections.get();
            try (Statement statement = connection.createStatement()) {
                InputStream s = ResultMetaTest.class.getResourceAsStream("/jdbc/foo.sql");
                String sqls = Strings.fromByteArray(s.readAllBytes());
                Arrays.stream(sqls.split(";\\s")).forEach((sql) -> {
                    try {
                        System.out.println(sql);
                        if (statement.execute(sql.strip())) {
                            try (ResultSet rs = statement.getResultSet()) {
                                while (rs.next()) {
                                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); ++i) {
                                        String key = rs.getMetaData().getColumnName(i);
                                        System.out.printf("\t%s -> %s\n", key, rs.getString(key));
                                    }
                                    System.out.println();
                                }
                            }
                        } else {
                            System.out.printf("UpdateCount: %d\n", statement.getUpdateCount());
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

}
