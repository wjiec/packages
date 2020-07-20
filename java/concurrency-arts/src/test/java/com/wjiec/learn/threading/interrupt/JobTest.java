package com.wjiec.learn.threading.interrupt;

import java.util.concurrent.TimeUnit;

public class JobTest {

    public static void main(String[] args) throws InterruptedException {
        Thread busy = new Thread(new BusyJob());
        busy.start();

        Thread sleep = new Thread(new SleepJob());
        sleep.start();

        TimeUnit.SECONDS.sleep(3);
        busy.interrupt();

        busy.join();
        System.out.println("interrupted and main exit");
    }

}
