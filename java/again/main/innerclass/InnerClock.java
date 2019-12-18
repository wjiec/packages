package main.innerclass;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InnerClock {

    String content;

    {
        content = "Content from outer";
    }

    void start() {
        Timer timer = new Timer(1000, new Printer());
        timer.start();
    }

    public static void main(String[] args) throws InterruptedException {
        new InnerClock().start();
        Thread.sleep(3000);
    }

    protected class Printer implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            System.out.println(content);
        }
    }

}
