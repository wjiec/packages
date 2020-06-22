package com.wjiec.learn.threading;

public class Concurrency {

    private long count;

    public Concurrency(long count) {
        this.count = count;
    }

    public long run() throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread thread = new Thread(() -> {
            int a = 0;
            for (int i = 0; i < count; i++) {
                a += 1;
            }
        });

        thread.start();
        int b = 0;
        for (int i = 0; i < count; i++) {
            b--;
        }
        thread.join();

        return System.currentTimeMillis() - start;
    }

}
