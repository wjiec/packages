package com.wjiec.learn.threading;

import java.util.Arrays;

public class ThreadingBench {

    public static void main(String[] args) {
        Arrays.stream(new long[] {1_0000, 10_0000, 100_0000, 1000_0000, 1_0000_0000, 10_0000_0000}).forEach((c) -> {
            try {
                System.out.printf("Concurrency(count=%d) time = %d\n", c, new Concurrency(c).run());
                System.out.printf("Serial(count=%d) time = %d\n", c, new Serial(c).run());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

}
