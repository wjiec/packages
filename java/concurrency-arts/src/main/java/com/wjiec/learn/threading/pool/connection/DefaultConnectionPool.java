package com.wjiec.learn.threading.pool.connection;


import com.wjiec.learn.threading.pool.connection.driver.ConnectionDriver;

import java.sql.Connection;
import java.util.LinkedList;

public class DefaultConnectionPool implements ConnectionPoolInterface {

    private final LinkedList<Connection> connections;

    public DefaultConnectionPool(int capacity) {
        connections = new LinkedList<>();
        for (int i = 0; i < capacity; i++) {
            connections.add(ConnectionDriver.createConnection());
        }
    }

    @Override
    public Connection getConnection(long mills) {
        synchronized (connections) {
            try {
                if (mills <= 0) {
                    while (connections.isEmpty()) {
                        connections.wait();
                    }
                    return connections.removeFirst();
                }

                long future = System.currentTimeMillis() + mills;
                long remaining = mills;
                while (connections.isEmpty() && remaining > 0) {
                    connections.wait(remaining);
                    remaining = future - System.currentTimeMillis();
                }

                if (connections.isEmpty()) {
                    return null;
                }
                return connections.removeFirst();
            } catch (InterruptedException e) {
                return null;
            }
        }
    }

    @Override
    public void release(Connection connection) {
        synchronized (connections) {
            connections.addLast(connection);
            connections.notifyAll();
        }
    }

}
