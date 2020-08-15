package com.wjiec.learn.executor;

import java.util.concurrent.*;

public class FutureTaskTask {

    private static final ConcurrentMap<String, Future<Integer>> taskCache;

    static {
        taskCache = new ConcurrentHashMap<>();
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch latch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    start.await();
                } catch (InterruptedException ignored) {}

                try {
                    System.out.println(executionTask("apple"));
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }

                latch.countDown();
            }).start();
        }

        start.countDown();
        latch.await();
    }

    private static Integer executionTask(String taskName) throws ExecutionException, InterruptedException {
        while (true) {
            Future<Integer> future = taskCache.get(taskName);
            if (future == null) {
                FutureTask<Integer> futureTask = new FutureTask<>(() -> (int)(Math.random() * 1000));
                future = taskCache.putIfAbsent(taskName, futureTask);
                if (future == null) {
                    future = futureTask;
                    futureTask.run();
                }
            }

            try {
                return future.get();
            } catch (CancellationException e) {
                taskCache.remove(taskName);
            }
        }
    }

}
