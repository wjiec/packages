package tests.asynctask;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<Integer> computation = () -> {
            int sleep = (int)(Math.random() * 5000);
            System.out.println("do working: " + sleep);

            Thread.sleep(sleep);
            return sleep;
        };

        FutureTask<Integer> task = new FutureTask<>(computation);

        new Thread(task).start();
        System.out.println("result: " + task.get());
    }

}
