package com.wjiec.learn.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorTest {

    public static void main(String[] args) throws InterruptedException {
        int cpu = Runtime.getRuntime().availableProcessors();
        System.out.println("Number of available processors: " + cpu);

        ThreadPoolExecutor executor = new ThreadPoolExecutor(cpu, cpu * 2,
            15, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2048));

        CountDownLatch latch = new CountDownLatch(200);
        for (int i = 0; i < 5000; i++) {
            final int index = i;
            executor.execute(() -> {
                try {
                    System.out.printf("thread-%d started\n", index);
                    TimeUnit.MILLISECONDS.sleep((int)(Math.random() * 100));
                    System.out.printf("thread-%d completed\n", index);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                latch.countDown();
            });
        }

        latch.await();
        executor.shutdown();
        System.out.println(executor.isShutdown());
        System.out.println(executor.isTerminated());
    }

}
