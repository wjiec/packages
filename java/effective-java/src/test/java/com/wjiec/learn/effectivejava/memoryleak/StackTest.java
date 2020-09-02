package com.wjiec.learn.effectivejava.memoryleak;

public class StackTest {

    public static void main(String[] args) {
        Stack stack = new Stack();
        for (int i = 0; i < 32; i++) {
            stack.push(new Stack());
        }

        for (int i = 0; i < 32; i++) {
            System.out.println(stack.pop());
        }
    }

}
