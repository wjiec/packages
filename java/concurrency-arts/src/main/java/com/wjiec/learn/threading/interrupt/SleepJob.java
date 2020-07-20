package com.wjiec.learn.threading.interrupt;

import java.util.concurrent.TimeUnit;

public class SleepJob implements Runnable {

    @Override
    public void run() {
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
