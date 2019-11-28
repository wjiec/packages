package tests.interfaces.callback;

import main.interfaces.callback.Clock;

import javax.swing.*;

public class ClockTest {

    public static void main(String[] args) throws InterruptedException {
        Timer timer = new Timer(1000, new Clock());
        timer.start();

        Thread.sleep(10000);
        timer.stop();
    }

}
