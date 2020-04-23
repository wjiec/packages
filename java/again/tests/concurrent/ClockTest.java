package tests.concurrent;

import main.concurrent.Clock;

public class ClockTest {

    public static void main(String[] args) {
        Clock clock = new Clock();
        for (int i = 0; i < 10; i++) {
            int tmp = i;
            new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    if (tmp % 2 == 1) {
                        clock.plus(j);
                    } else {
                        try {
                            // dead lock
                            clock.minus(j + tmp);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }

}
