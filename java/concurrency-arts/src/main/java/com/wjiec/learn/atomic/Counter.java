package com.wjiec.learn.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter {

    private int i;

    private AtomicInteger ai;

    public Counter() {
        i = 0;
        ai = new AtomicInteger(0);
    }

    public void incr() {
        i++;
    }

    public void safeIncr() {
        while (true) {
            int i = ai.get();
            if (ai.compareAndSet(i, i + 1)) {
                break;
            }
        }
    }

    public int getI() {
        return i;
    }

    public int getAI() {
        return ai.get();
    }

}
