package com.wjiec.learn.effectivejava.builder.builder;

public class UniquePizzaTest {

    public static void main(String[] args) {
        System.out.println(new UniquePizza.Builder().build());
        System.out.println(new UniquePizza.Builder().build());
        System.out.println(new UniquePizza.Builder().build());
    }

}
