package com.wjiec.learn.effectivejava.cloneable;

public class ArrayCloneTest {

    public static void main(String[] args) {
        Element[] elements = new Element[10];
        for (int i = 0; i < 10; i++) {
            elements[i] = new Element(i);
        }

        Element[] otherElements = elements.clone();
        for (int i = 0; i < elements.length; i++) {
            System.out.printf("%s %s\n", elements[i], otherElements[i]);
            System.out.printf("%b\n", elements[i] == otherElements[i]);
        }
    }

}
