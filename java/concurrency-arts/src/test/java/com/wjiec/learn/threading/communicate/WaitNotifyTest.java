package com.wjiec.learn.threading.communicate;

import java.util.concurrent.TimeUnit;

public class WaitNotifyTest {

    private static boolean flag = false;
    private static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread wait = new Thread(new Wait());
        wait.start();

        TimeUnit.SECONDS.sleep(1);

        Thread notify = new Thread(new Notify());
        notify.start();

        wait.join();
        notify.join();
    }

    static class Wait implements Runnable {

        @Override
        public void run() {
            synchronized (lock) {
                while (!flag) {
                    try {
                        System.out.printf("%s: wait check failure, flag = %s, waiting...\n",
                            System.currentTimeMillis(), flag);
                        lock.wait();
                    } catch (InterruptedException ignored) {}
                }

                System.out.printf("%s: wait check success, flag = %s, do something...\n",
                    System.currentTimeMillis(), flag);
            }
        }

    }

    static class Notify implements Runnable {

        @Override
        public void run() {
            synchronized (lock) {
                System.out.printf("%s: notifier hold lock, check release...\n", System.currentTimeMillis());
                while (!flag) {
                    if (Math.random() < 0.3) {
                        System.out.printf("%s: notifier release lock...\n", System.currentTimeMillis());
                        flag = true;
                        lock.notifyAll();
                        break;
                    }
                }
            }

            synchronized (lock) {
                System.out.printf("%s: notifier hold lock again, sleep...\n", System.currentTimeMillis());
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException ignored) {}
            }
        }

    }

}
