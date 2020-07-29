package com.wjiec.learn.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class MultiMutexTest {

    private static final int THREAD_COUNT = 16;

    public static void main(String[] args) throws InterruptedException {
        MultiLock mutex = new MultiLock(2);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch threads = new CountDownLatch(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(() -> {
                try {
                    start.await();
                } catch (InterruptedException ignored) {}

                mutex.lock();
                System.out.printf("Thread(%s) acquire...\n", Thread.currentThread().getName());
                try {
                    TimeUnit.MILLISECONDS.sleep((int)(Math.random() * 1000));
                } catch (InterruptedException ignored) {} finally {
                    mutex.unlock();
                }

                threads.countDown();
            }, "thread-" + i).start();
        }

        start.countDown();
        threads.await();
    }

}
