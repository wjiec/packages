package tests.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

public class Atomic {

    public static void main(String[] args) {
        AtomicInteger integer = new AtomicInteger();
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    System.out.println(integer.incrementAndGet());
                }
            }).start();
        }
    }

}
