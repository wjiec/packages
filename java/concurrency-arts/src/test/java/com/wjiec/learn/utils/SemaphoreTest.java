package com.wjiec.learn.utils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreTest {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(20);
        Semaphore semaphore = new Semaphore(8);
        for (int i = 0; i < 20; i++) {
            Thread t = new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + " acquired");
                    TimeUnit.MILLISECONDS.sleep((int)(Math.random() * 1000));
                    semaphore.release();
                    System.out.println(Thread.currentThread().getName() + " released");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + " acquired again");
                    TimeUnit.MILLISECONDS.sleep((int)(Math.random() * 1000));
                    semaphore.release();
                    System.out.println(Thread.currentThread().getName() + " released again");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                latch.countDown();
            });

            t.setName("thread-" + i);
            t.start();
        }

        latch.await();
    }

}
