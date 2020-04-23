package main.concurrent;

public class Clock {

    private int clock;

    public Clock() {
        clock = 0;
    }

    public synchronized void plus(int time) {
        clock += time;
        System.out.printf("plus clock: %d into %s\n", time, clock);

        notifyAll();
    }

    public synchronized void minus(int time) throws InterruptedException {
        if (clock < time) {
            System.out.println("waiting for lock");
            wait();
        }

        clock -= time;
        System.out.printf("minus clock: %d into %s\n", time, clock);
    }

}
