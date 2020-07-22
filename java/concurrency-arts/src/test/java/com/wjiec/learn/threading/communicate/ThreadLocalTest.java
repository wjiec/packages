package com.wjiec.learn.threading.communicate;

public class ThreadLocalTest implements Runnable {

    private static ThreadLocal<Double> number = ThreadLocal.withInitial(Math::random);

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " initialValue: " + number.get());
        number.set(Math.random());
        System.out.println(Thread.currentThread().getName() + " afterNumber: " + number.get());
    }

    public static void main(String[] args) {
        System.out.println("main: " + number.get());

        Thread thread1 = new Thread(new ThreadLocalTest());
        thread1.start();

        System.out.println("main thread1 after: " + number.get());

        Thread thread2 = new Thread(new ThreadLocalTest());
        thread2.start();

        System.out.println("main thread2 after: " + number.get());

    }

}
