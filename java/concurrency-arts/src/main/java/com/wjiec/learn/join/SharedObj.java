package com.wjiec.learn.join;

public class SharedObj {

    private volatile int num;

    public SharedObj() {
        this.num = 0;
    }

    public int get() {
        return num;
    }

    public void set(int n) {
        this.num = n;
        System.out.printf("set to %d\n", n);
    }

}
