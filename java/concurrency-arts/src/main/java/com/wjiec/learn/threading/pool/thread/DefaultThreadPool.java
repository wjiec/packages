package com.wjiec.learn.threading.pool.thread;

import com.wjiec.learn.threading.pool.thread.worker.Worker;

import java.util.LinkedList;
import java.util.List;

public class DefaultThreadPool<Job extends Runnable> implements ThreadPoolInterface<Job> {

    private List<Worker> workers;
    private LinkedList<Job> jobs;

    public DefaultThreadPool() {
        this(Runtime.getRuntime().availableProcessors() * 2);
    }

    public DefaultThreadPool(int capacity) {
        jobs = new LinkedList<>();
        workers = new LinkedList<>();
        for (int i = 0; i < capacity; i++) {
            Worker worker = new Worker(jobs);
            workers.add(worker);

            new Thread(worker, "worker-" + i).start();
        }
    }

    @Override
    public void execute(Job job) {
        synchronized (jobs) {
            jobs.addLast(job);
            jobs.notify();
        }
    }

    @Override
    public void shutdown() {
        for (Worker worker : workers) {
            worker.shutdown();
        }
    }

    @Override
    public int getWorkCount() {
        return workers.size();
    }

}
