package com.wjiec.learn.threading;

public class Serial {

    private long count;

    public Serial(long count) {
        this.count = count;
    }

    public long run() {
        long start = System.currentTimeMillis();

        int a = 0;
        for (int i = 0; i < count; i++) {
            a += 1;
        }

        int b = 0;
        for (int i = 0; i < count; i++) {
            b--;
        }

        return System.currentTimeMillis() - start;
    }

}
