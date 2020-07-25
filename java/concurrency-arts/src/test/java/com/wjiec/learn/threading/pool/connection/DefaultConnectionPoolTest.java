package com.wjiec.learn.threading.pool.connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class DefaultConnectionPoolTest {

    private static final int RUNNING_THREAD_SIZE = 100;
    private static final int CONNECTION_LOOP_COUNT = 100;
    private static DefaultConnectionPool pool = new DefaultConnectionPool(10);
    private static CountDownLatch start = new CountDownLatch(1);
    private static CountDownLatch thread = new CountDownLatch(RUNNING_THREAD_SIZE);

    public static void main(String[] args) throws InterruptedException {
        AtomicInteger success = new AtomicInteger(0);
        AtomicInteger failure = new AtomicInteger(0);
        for (int i = 0; i < RUNNING_THREAD_SIZE; i++) {
            Thread thread = new Thread(new ConnectionRunner(CONNECTION_LOOP_COUNT, success, failure));
            thread.start();
        }

        start.countDown();
        thread.await();

        System.out.printf("Invoke count: %d, Success count: %d, Failure count: %d",
            RUNNING_THREAD_SIZE * CONNECTION_LOOP_COUNT, success.get(), failure.get());
    }

    static class ConnectionRunner implements Runnable {

        private int count;
        private AtomicInteger success;
        private AtomicInteger failure;

        ConnectionRunner(int count, AtomicInteger success, AtomicInteger failure) {
            this.count = count;
            this.success = success;
            this.failure = failure;
        }

        @Override
        public void run() {
            try {
                start.await();
            } catch (InterruptedException ignored) {}

            for (int i = 0; i < count; i++) {
                Connection connection = pool.getConnection(1000);
                if (connection != null) {
                    try {
                        connection.createStatement();
                        connection.commit();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        pool.release(connection);
                        success.incrementAndGet();
                    }
                } else {
                    failure.incrementAndGet();
                }
            }

            thread.countDown();
        }

    }

}
