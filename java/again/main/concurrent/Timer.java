package main.concurrent;

public class Timer implements Runnable {

    protected int interval;

    public Timer(int interval) {
        this.interval = interval;
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println("Timer" + interval + " running...");
                Thread.sleep(interval * 1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
