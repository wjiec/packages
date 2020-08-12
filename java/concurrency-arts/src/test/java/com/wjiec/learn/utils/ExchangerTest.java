package com.wjiec.learn.utils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Exchanger;

public class ExchangerTest {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(9);
        Exchanger<Pair<String, Integer>> exchanger = new Exchanger<>();
        for (int i = 0; i < 9; i++) {
            Thread t = new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    int value = (int)(Math.random() * 10);

                    try {
                        Pair<String, Integer> ov = exchanger.exchange(new Pair<>(Thread.currentThread().getName(), value));
                        System.out.printf("%s: exchange %d into <%s:%d>\n",
                            Thread.currentThread().getName(), value, ov.k, ov.v);
                    } catch (InterruptedException ignored) {}
                }

                latch.countDown();
            });

            t.setName("thread-" + i);
            t.start();
        }

        latch.await();
    }

    static class Pair<K, V> {

        private K k;
        private V v;

        private Pair(K k, V v) {
            this.k = k;
            this.v = v;
        }
    }

}
