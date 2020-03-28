package tests.concurrent;

import main.concurrent.Timer;

public class TimerTest {

    public static void main(String[] args) throws InterruptedException {
        Timer[] timers = new Timer[10];
        for (int i = 0; i < timers.length; ++i) {
            timers[i] = new Timer(i + 1);
        }

        Thread[] threads = new Thread[timers.length];
        for (int i = 0; i < threads.length; ++i) {
            threads[i] = new Thread(timers[i]);
            threads[i].start();
        }

        Thread.sleep(10000);
    }

}
