package com.wjiec.learn.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ReentrantTest {

    private static final int THREAD_COUNT = 10;
    private static final Reentrant lock = new Reentrant();
    private static final CountDownLatch start = new CountDownLatch(1);
    private static final CountDownLatch thread = new CountDownLatch(THREAD_COUNT);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(new Worker()).start();
        }

        start.countDown();
        thread.await();
    }

    static class Worker implements Runnable {

        @Override
        public void run() {
            try {
                start.await();
            } catch (InterruptedException ignored) {}

            lock.lock();
            try {
                System.out.printf("%s locking...\n", Thread.currentThread().getName());
                printInfo();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

            thread.countDown();
        }

        private void printInfo() throws InterruptedException {
            lock.lock();
            try {
                System.out.printf("%s: printInfo before\n", Thread.currentThread().getName());
                TimeUnit.MILLISECONDS.sleep((int)(Math.random() * 1000));
                System.out.printf("%s: printInfo after\n", Thread.currentThread().getName());
            } finally {
                lock.unlock();
            }
        }

    }

}
