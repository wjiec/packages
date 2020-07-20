package com.wjiec.learn.threading.interrupt;

import java.util.concurrent.TimeUnit;

public class BusyJob implements Runnable {

    @Override
    public void run() {
        while (true) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("thread interrupted");
            } else {
                System.out.println("thread waiting signal");
            }
        }
    }

}
