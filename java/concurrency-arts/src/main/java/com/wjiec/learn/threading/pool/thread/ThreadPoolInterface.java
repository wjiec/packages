package com.wjiec.learn.threading.pool.thread;

public interface ThreadPoolInterface<Job extends Runnable> {

    void execute(Job work);

    void shutdown();

    int getWorkCount();

}
