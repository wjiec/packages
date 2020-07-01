package com.wjiec.learn.reordering;

public class SynchronizedThreading {

    private int number = 0;

    private boolean flag = false;

    public synchronized void write() {
        number = 1;
        flag = true;
    }

    public synchronized int read() {
        if (flag) {
            return number * number;
        }
        return -1;
    }

}
