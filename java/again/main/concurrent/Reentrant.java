package main.concurrent;

import java.util.concurrent.locks.ReentrantLock;

public class Reentrant {

    private ReentrantLock lock;

    private Reentrant() {
        lock = new ReentrantLock();
    }

    private void transfer() {
        System.out.println("waiting lock");
        lock.lock();
        try {
            System.out.println("do lock");
            Thread.sleep((int)(Math.random() * 500));
            total();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void total() {
        lock.lock();
        try {
            System.out.println("lock again");
            Thread.sleep((int)(Math.random() * 500));
        }  catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        Reentrant reentrant = new Reentrant();
        for (int i = 0; i < 10; i++) {
            new Thread(reentrant::transfer).start();
        }
    }

}
