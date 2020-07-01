package com.wjiec.learn.reordering;

public class ThreadingReordering {

    private int number = 0;

    private boolean flag = false;

    public void write() {
        number = 1;
        flag = true;
    }

    public int read() {
        if (flag) {
            return number * number;
        }
        return -1;
    }

}
