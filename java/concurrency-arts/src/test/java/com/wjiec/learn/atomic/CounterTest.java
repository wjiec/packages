package com.wjiec.learn.atomic;

import java.util.LinkedList;
import java.util.List;

public class CounterTest {

    public static void main(String[] args) {
        Counter counter = new Counter();
        List<Thread> threads = new LinkedList<>();
        for (int i = 0; i < 100; i++) {
            threads.add(new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    counter.incr();
                    counter.safeIncr();
                }
            }));
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(counter.getI());
        System.out.println(counter.getAI());
    }

}
