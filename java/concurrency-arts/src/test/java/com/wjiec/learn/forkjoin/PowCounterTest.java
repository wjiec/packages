package com.wjiec.learn.forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

public class PowCounterTest {

    private static final int NUMBERS_SIZE = 1000;

    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>(NUMBERS_SIZE);
        for (int i = 0; i < NUMBERS_SIZE; i ++) {
            numbers.add((int)(Math.random() * NUMBERS_SIZE));
        }

        ForkJoinPool pool = new ForkJoinPool();
        PowCounter counter = new PowCounter(numbers, 0, numbers.size());
        Future<Integer> result = pool.submit(counter);
        try {
            System.out.println(result.get());
        } catch (InterruptedException| ExecutionException e) {
            e.printStackTrace();
        }
    }

}
