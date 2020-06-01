package com.github.wjiec.jdbc;

import java.sql.*;

public class MetadataTest {

    public static void main(String[] args) {
        try {
            Connection connection = Connections.get();
            DatabaseMetaData metaData = connection.getMetaData();

            try (ResultSet rs = metaData.getTables(null, null, null, new String[] {"TABLE"})) {
                while (rs.next()) {
                    ResultSetMetaData rsMetaData = rs.getMetaData();
                    for (int i = 1; i < rsMetaData.getColumnCount(); i++) {
                        String key = rsMetaData.getColumnName(i);
                        System.out.printf("%s: %s\n", key, rs.getString(key));
                    }
                    System.out.println();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
