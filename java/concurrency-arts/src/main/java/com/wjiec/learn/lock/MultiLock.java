package com.wjiec.learn.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class MultiLock implements Lock {

    private final MultiSync sync;

    public MultiLock(int count) {
        sync = new MultiSync(count);
    }

    @Override
    public void lock() {
        sync.acquireShared(1);
    }

    @Override
    public void unlock() {
        sync.releaseShared(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireSharedInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Condition newCondition() {
        return sync.createCondition();
    }

    static class MultiSync extends AbstractQueuedSynchronizer {

        MultiSync(int count) {
            setState(count);
        }

        @Override
        protected int tryAcquireShared(int arg) {
            while (true) {
                int remaining = getState();
                int nextState = remaining - arg;
                if (nextState < 0 || compareAndSetState(remaining, nextState)) {
                    return nextState;
                }
            }
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            while (true) {
                int remaining = getState();
                int nextState = remaining + arg;
                if (compareAndSetState(remaining, nextState)) {
                    return true;
                }
            }
        }

        Condition createCondition() {
            return new ConditionObject();
        }

    }

}
