package main.innerclass;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AnonymousClass {

    private static String content = "outer";

    public static void main(String[] args) throws InterruptedException {
        new Timer(1000, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println(content);
            }
        }).start();
        Thread.sleep(3000);
    }

}
