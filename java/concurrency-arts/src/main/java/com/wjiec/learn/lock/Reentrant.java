package com.wjiec.learn.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Reentrant implements Lock {

    private final ReentrantSync sync = new ReentrantSync();

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.createCondition();
    }

    static class ReentrantSync extends AbstractQueuedSynchronizer {
        @Override
        protected boolean tryAcquire(int arg) {
            int currentState = getState();
            Thread currentThread = Thread.currentThread();
            if (currentState == 0 && compareAndSetState(currentState, arg)) {
                setExclusiveOwnerThread(currentThread);
                return true;
            } else if (getExclusiveOwnerThread() == currentThread) {
                // always the same thread
                setState(currentState + arg);
                return true;
            }

            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            if (Thread.currentThread() != getExclusiveOwnerThread()) {
                throw new IllegalMonitorStateException();
            }

            // always the same thread
            int nextState = getState() - arg;
            setState(nextState);
            if (nextState == 0) {
                setExclusiveOwnerThread(null);
                return true;
            }
            return false;
        }

        Condition createCondition() {
            return new ConditionObject();
        }

    }

}
