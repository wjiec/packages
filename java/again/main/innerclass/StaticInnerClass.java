package main.innerclass;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StaticInnerClass {

    private static String content = "outer";

    public static void main(String[] args) throws InterruptedException {
        new Timer(1000, new Printer());
        Thread.sleep(3000);
    }

    static class Printer implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            System.out.println();
        }
    }

}
