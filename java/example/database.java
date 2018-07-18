package com.shellboot;

import com.alibaba.fastjson.JSONObject;
import java.io.*;
import java.sql.*;

public class App  {
    public static void main(String[] args) {
        try {
            FileInputStream stream = new FileInputStream("D:/qiqi.txt");
            InputStreamReader streamReader = new InputStreamReader(stream, "utf-8");
            BufferedReader reader = new BufferedReader(streamReader);

            connectDatabase();

            String line;
            while ((line = reader.readLine()) != null) {
                saveToDatabase(line);
            }

            closeDatabase();

            reader.close();
            streamReader.close();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveToDatabase(String line) {
        JSONObject object = JSONObject.parseObject(line);

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into `chain` " +
                "(`txhash`, `prev_txhash`, `block_height`, `tx_receipt_status`, `time_stamp`, `from_address`, `to_address`, `value`, `gas_tx_limit`, `gas_tx_used`, `gas_price`, `actual_tx_fee`, `nonce`, `input_data`, `tx_type`, `order`) " +
                " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            preparedStatement.setString(1, (String) object.get("txhash"));
            preparedStatement.setString(2, (String) object.get("prev_txhash"));
            preparedStatement.setInt(3, (Integer) object.get("block_height"));
            preparedStatement.setBoolean(4, (Integer) object.get("tx_receipt_status") == 1);
            preparedStatement.setInt(5, (Integer) object.get("time_stamp"));
            preparedStatement.setString(6, (String) object.get("from_address"));
            preparedStatement.setString(7, (String) object.get("to_address"));
            preparedStatement.setString(8, (String) object.get("value"));
            preparedStatement.setInt(9, (Integer) object.get("gas_tx_limit"));
            preparedStatement.setInt(10, (Integer) object.get("gas_tx_used"));
            preparedStatement.setString(11, (String) object.get("gas_price"));
            preparedStatement.setString(12, (String) object.get("actual_tx_fee"));
            preparedStatement.setInt(13, (Integer) object.get("nonce"));
            preparedStatement.setString(14, (String) object.get("input_data"));
            preparedStatement.setInt(15, (Integer) object.get("tx_type"));
            preparedStatement.setString(16, (String) object.get("order"));

            preparedStatement.addBatch();
            preparedStatement.executeBatch();

            System.out.println(String.format("%s saved to database", object.get("txhash")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void connectDatabase() {
        try {
            Class.forName(driver).newInstance();
            connection = DriverManager.getConnection(url + database, username, password);
            if (connection.isClosed()) {
                System.err.println("open connection failure");
                System.exit(1);
            }

            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void closeDatabase() {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static Connection connection;

    private static Statement statement;

    private static String driver = "com.mysql.cj.jdbc.Driver";

    private static String url = "jdbc:mysql://192.168.199.124:3306/";

    private static String database = "qiqi";

    private static String username = "root";

    private static String password = "root";
}
