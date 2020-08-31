package com.wjiec.learn.effectivejava.reuse;

public class BoxingTest {

    public static void main(String[] args) {
        System.out.println("boxing: " + boxing());
        System.out.println("primary: " + primary());
    }

    private static long boxing() {
        long start = System.currentTimeMillis();

        Long sum = 0L;
        for (long i = 0; i < Integer.MAX_VALUE; i++) {
            sum += i;
        }
        System.out.println(sum);

        return System.currentTimeMillis() - start;
    }

    private static long primary() {
        long start = System.currentTimeMillis();

        long sum = 0L;
        for (long i = 0; i < Integer.MAX_VALUE; i++) {
            sum += i;
        }
        System.out.println(sum);

        return System.currentTimeMillis() - start;
    }

}
