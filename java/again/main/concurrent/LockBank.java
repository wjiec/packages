package main.concurrent;

import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

public class LockBank {

    private double[] accounts;

    private ReentrantLock lock;

    public LockBank(int count, double initial) {
        accounts = new double[count];
        Arrays.fill(accounts, initial);

        lock = new ReentrantLock();
    }

    public void transfer(int from, int to, double amount) {
        lock.lock();
        try {
            accounts[from] -= amount;
            accounts[to] += amount;
            System.out.printf("from %d transfer to %d amount %f\n", from, to, amount);
            System.out.println("total: " + total());
        } finally {
            lock.unlock();
        }
    }

    private double total() {
        return Arrays.stream(accounts).reduce(0, Double::sum);
    }

}
