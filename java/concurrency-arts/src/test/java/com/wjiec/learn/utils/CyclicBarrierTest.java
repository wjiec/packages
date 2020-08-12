package com.wjiec.learn.utils;

import org.junit.experimental.theories.Theories;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class CyclicBarrierTest {

    private static final int THREAD_COUNT = 5;

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(THREAD_COUNT, () -> {
            System.out.println("BarrierAction started");
        });

        for (int i = 0; i < THREAD_COUNT - 1; i++) {
            Thread t = new Thread(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep((int) (Math.random() * 1000));
                    System.out.println(Thread.currentThread().getName() + "arrived");
                } catch (InterruptedException ignored) {
                    System.out.println(Thread.currentThread().getName() + "interrupted");
                }

                try {
                    barrier.await();
                } catch (InterruptedException| BrokenBarrierException ignored) {
                    System.out.println("barrier broken: " + barrier.isBroken());
                }
            });
            t.setName("thread-" +i);
            t.start();

            if (Math.random() > 0.95) {
                t.interrupt();
            }
        }

        try {
            barrier.await();
        } catch (InterruptedException|BrokenBarrierException e) {
            e.printStackTrace();
        }

        System.out.println("barrier broken: " + barrier.isBroken());
    }

}
