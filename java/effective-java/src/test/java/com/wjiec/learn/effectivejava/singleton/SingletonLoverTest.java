package com.wjiec.learn.effectivejava.singleton;

import java.util.concurrent.CountDownLatch;
import java.util.function.Supplier;

public class SingletonLoverTest {

    private static final int THREAD_COUNT = 100;

    public static void main(String[] args) {
        threading(() -> Lover.INSTANCE);
        threading(Lover::getInstance);
        threading(() -> EnumSingleton.INSTANCE);
        threading(() -> PerfectLover.INSTANCE);
        threading(PerfectLover::getInstance);
    }

    private static void threading(Supplier<?> supplier) {
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(() -> {
                try {
                    start.await();
                } catch (InterruptedException ignored) {}

                System.out.println(supplier.get());
                latch.countDown();
            }).start();
        }

        start.countDown();
        try {
            latch.await();
        } catch (InterruptedException ignored) {}
    }

}
