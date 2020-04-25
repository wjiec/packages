package tests.asynctask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.function.Predicate;

public class ForkJoinTest {

    private static final int SIZE = 10000000;

    public static void main(String[] args) {
        ArrayList<Double> numbers = new ArrayList<>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            numbers.add(Math.random());
        }

        Counter<Double> counter = new Counter<>(numbers, (v) -> v > 0.5);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(counter);

        System.out.println("total: " + counter.join());
    }

}

class Counter<T> extends RecursiveTask<Integer> {

    private static final int THRESHOLD = 1000;

    private List<T> collection;

    private int from;

    private int to;

    private Predicate<T> filter;

    Counter(List<T> c, Predicate<T> f) {
        this(c, 0, c.size(), f);
    }

    private Counter(List<T> c, int from, int to, Predicate<T> f) {
        collection = c;
        this.from = from;
        this.to = to;
        filter = f;
    }

    @Override
    protected Integer compute() {
        if (to - from < THRESHOLD) {
            int count = 0;
            for (int i = from; i < to; i++) {
                if (filter.test(collection.get(i))) {
                    count ++;
                }
            }
            return count;
        }

        int mid = (from + to) / 2;
        Counter<T> first = new Counter<>(collection, from, mid, filter);
        Counter<T> second = new Counter<>(collection, mid, to, filter);

        invokeAll(first, second);
        return first.join() + second.join();
    }

}