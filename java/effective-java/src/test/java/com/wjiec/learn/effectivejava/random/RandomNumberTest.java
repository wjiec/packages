package com.wjiec.learn.effectivejava.random;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomNumberTest {

    public static void main(String[] args) {
        int n = 2 * (Integer.MAX_VALUE / 3);
        int low = 0;
        for (int i = 0; i < 1000000; i++) {
            if (RandomNumber.random(n) < n / 2) {
                low++;
            }
        }
        System.out.println(low); // near 666666 why?

        low = 0;
        Random random = new Random();
        for (int i = 0; i < 1000000; i++) {
            if (random.nextInt(n) < n / 2) {
                low++;
            }
        }
        System.out.println(low); // it's ok

        low = 0;
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        for (int i = 0; i < 1000000; i++) {
            if (threadLocalRandom.nextInt(n) < n / 2) {
                low++;
            }
        }
        System.out.println(low); // it's faster
    }

}
