package com.wjiec.learn.threading.pool.connection;

import java.sql.Connection;

public interface ConnectionPoolInterface {

    Connection getConnection(long mills);

    void release(Connection connection);

}
