package tests.concurrent;

import main.concurrent.Bank;
import main.concurrent.LockBank;

public class LockBankTest {

    public static void main(String[] args) {
        LockBank bank = new LockBank(100, 1000);
        for (int i = 0; i < 100; ++i) {
            new Thread(() -> {
                try {
                    while (true) {
                        int from = (int)(100 * Math.random());
                        int to = (int)(100 * Math.random());
                        if (from != to) {
                            bank.transfer(from, to, Math.random() * 1000);
                        }

                        Thread.sleep((int)(1000 * Math.random()));
                    }
                } catch (InterruptedException ignored) {}
            }).start();
        }
    }

}
