package com.wjiec.learn.effectivejava.random;

import java.util.Random;

public class RandomNumber {

    static Random generator = new Random();

    public static int random(int n) {
        return Math.abs(generator.nextInt()) % n;
    }

}
