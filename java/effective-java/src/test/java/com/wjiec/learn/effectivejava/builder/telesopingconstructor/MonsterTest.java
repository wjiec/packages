package com.wjiec.learn.effectivejava.builder.telesopingconstructor;

import com.wjiec.learn.effectivejava.builder.telescopingconstructor.Monster;

public class MonsterTest {

    public static void main(String[] args) {
        System.out.println(new Monster("apple"));
        System.out.println(new Monster("banana", 20));
        System.out.println(new Monster("cherry", 30, 5));
        System.out.println(new Monster("dew", 40, 500, 500));
    }

}
