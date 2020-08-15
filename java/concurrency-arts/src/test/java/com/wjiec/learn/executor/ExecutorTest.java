package com.wjiec.learn.executor;

import java.util.concurrent.*;

public class ExecutorTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        FutureTask<String> result = (FutureTask<String>) executor.submit(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep((int)(Math.random() * 1000));
            } catch (InterruptedException ignored) {}

            return "task result";
        });

        System.out.println(result.get());
        executor.shutdownNow();
    }

}
