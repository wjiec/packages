package com.wjiec.learn.threading.pool.thread.worker;

import java.util.LinkedList;

public class Worker implements Runnable {

    private volatile boolean running;
    private final LinkedList<? extends Runnable> jobs;

    public Worker(LinkedList<? extends Runnable> jobs) {
        this.jobs = jobs;
        this.running = true;
    }

    @Override
    public void run() {
        while (this.running) {
            try {
                Runnable job = null;
                synchronized (jobs) {
                    while (jobs.isEmpty()) {
                        jobs.wait();
                    }
                    job = jobs.removeFirst();
                }

                job.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void shutdown() {
        this.running = false;
    }

}
