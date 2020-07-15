package com.wjiec.learn.delay;

public class InstanceFactoryTest {

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[100];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                try {
                    Thread.sleep(3000);
//                    Thread.sleep((int)(Math.random() * 5000));
                    System.out.println(InstanceFactory.getInstance());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        for (var t : threads) {
            t.start();
        }

        for (var t : threads) {
            t.join();
        }
    }

}
