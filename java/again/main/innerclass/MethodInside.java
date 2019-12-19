package main.innerclass;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MethodInside {

    private static String content;

    static {
        content = "contents from outer";
    }

    public static void main(String[] args) throws InterruptedException {
        class Printer implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println(content);
            }

        }

        new Timer(1000, new Printer()).start();
        Thread.sleep(3000);
    }

}
