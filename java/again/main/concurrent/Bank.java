package main.concurrent;

import java.util.Arrays;

public class Bank {

    private double[] accounts;

    public Bank(int count, double initial) {
        accounts = new double[count];
        Arrays.fill(accounts, initial);
    }

    public void transfer(int from, int to, double amount) {
        accounts[from] -= amount;
        accounts[to] += amount;
        System.out.printf("from %d transfer to %d amount %f\n", from, to, amount);
        System.out.println("total: " + total());
    }

    private double total() {
        return Arrays.stream(accounts).reduce(0, Double::sum);
    }

}
