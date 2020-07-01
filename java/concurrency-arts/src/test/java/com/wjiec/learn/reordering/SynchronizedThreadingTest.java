package com.wjiec.learn.reordering;

public class SynchronizedThreadingTest {

    public static void main(String[] args) throws InterruptedException {
        SynchronizedThreading o = new SynchronizedThreading();
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(1000);
                o.write();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
                System.out.println(o.read());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

}
