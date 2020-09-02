package com.wjiec.learn.effectivejava.memoryleak;

import java.util.EmptyStackException;

public class Stack {

    private static final int DEFAULT_STACK_CAPACITY = 64;
    private final Object[] elements;
    private int index = 0;

    public Stack() {
        this(DEFAULT_STACK_CAPACITY);
    }

    public Stack(int size) {
        elements = new Object[size];
    }

    public Object pop() {
        if (index == 0) {
            throw new EmptyStackException();
        }

        // index -= 1, elements[index]
        return elements[--index]; // memory leak(unintentional object retention)

        // Object result = element[--index];
        // element[index] = null;
        // return result;
    }

    public void push(Object el) {
        if (index == elements.length - 1) {
            throw new StackOverflowError();
        }

        // elements[index] = el, index += 1
        elements[index++] = el;
    }

}
