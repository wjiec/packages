package com.wjiec.learn.effectivejava.nestedclass;

import java.util.concurrent.atomic.AtomicInteger;

public class NestedClass {

    private static final AtomicInteger ai = new AtomicInteger();
    private final long id;

    public NestedClass() {
        id = ai.incrementAndGet();
    }

    public static void main(String[] args) {
        NestedClass nestedClass = new NestedClass();
        StaticNestedClass staticNestedClass = new NestedClass.StaticNestedClass();
        NonStaticNestedClass nonStaticNestedClass = nestedClass.new NonStaticNestedClass();
        staticNestedClass.print();
        nonStaticNestedClass.print();
    }

    private static class StaticNestedClass {
        void print() {
            System.out.println(ai);
        }
    }

    private class NonStaticNestedClass {
        void print() {
            System.out.println(id);
            System.out.println(NestedClass.this.id);
        }
    }

}
