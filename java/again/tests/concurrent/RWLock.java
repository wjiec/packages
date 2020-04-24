package tests.concurrent;

import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RWLock {

    public static void main(String[] args) {
        Vector<Integer> number = new Vector<>() {{ add(1); }};

        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        Lock read = lock.readLock();
        Lock write = lock.writeLock();

        for (int i = 0; i < 3; i++) {
            final int fi = i;
            new Thread(() -> {
                try {
                    read.lock();
                    for (int j = 0; j < 100; j++) {
                        System.out.printf("read %d is %d\n", fi, number.get(0));

                        Thread.sleep(10);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    read.unlock();
                }
            }).start();
        }

        for (int i = 0; i < 10; i++) {
            final int fi = i;
            new Thread(() -> {
                try {
                    write.lock();
                    for (int j = 0; j < 100; j++) {
                        number.set(0, fi);
                    }
                } finally {
                    write.unlock();
                }
            }).start();
        }
    }

}
