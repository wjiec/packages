package com.wjiec.learn.effectivejava.singleton;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class Ring implements Serializable {

    private static final AtomicInteger ai = new AtomicInteger();
    private final int id;

    public Ring() {
        id = ai.incrementAndGet();
        System.out.println("Ring.Ring constructor");
    }

    public int getId() {
        return id;
    }

}
