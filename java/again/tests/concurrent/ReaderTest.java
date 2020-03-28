package tests.concurrent;

import main.concurrent.Reader;

public class ReaderTest {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Reader(System.in));
        thread.setUncaughtExceptionHandler((Thread t, Throwable e) -> {
            System.out.println(String.format("In thread %s error: %s", t.getName(), e.getMessage()));
            System.out.println(String.format("Thread status: %s", t.getState().name()));
        });

        thread.start();
        Thread.sleep(10000000);
    }

}
