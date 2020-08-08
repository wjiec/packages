package com.wjiec.learn.forkjoin;

import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

public class PowCounter extends RecursiveTask<Integer> {

    private static final int THRESHOLD = 2;

    private final List<Integer> numbers;
    private final int start;
    private final int end;

    public PowCounter(List<Integer> numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if (end - start > THRESHOLD) {
            int middle = (end + start) / 2;

            System.out.printf("splitting task to %d-%s and %d-%d\n", start, middle, middle + 1, end);
            PowCounter left = new PowCounter(numbers, start, middle);
            PowCounter right = new PowCounter(numbers, middle + 1, end);

            left.fork();
            right.fork();

            return left.join() + right.join();
        } else {
            System.out.printf("summing numbers: %s-%s\n", start, end);

            int sum = 0;
            for (int i = start; i < end; i++) {
                sum += numbers.get(i) * numbers.get(i);
                try {
                    TimeUnit.MILLISECONDS.sleep(numbers.get(i));
                } catch (InterruptedException ignored) { }
            }
            return sum;
        }
    }

}
