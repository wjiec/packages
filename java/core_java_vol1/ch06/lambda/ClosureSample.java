package lambda;

import javax.swing.*;
import java.util.Date;

public class ClosureSample {
    public static void main(String[] args) {
        ClosureSample closure = new ClosureSample();
        closure.printMessage("printMessage");
        closure.printMessageError("printMessageError");

        JOptionPane.showMessageDialog(null, "Exit ?");
        System.exit(0);
    }

    private void printMessage(String text) {
        Timer timer = new Timer(1000, e -> {
            System.out.println(new Date() + " `" + text + "` " + e);
        });

        timer.start();
    }

    private void printMessageError(String text) {
        for (int i = 0; i < 5; i++) {
            Timer timer = new Timer(1000, e -> {
//                System.out.println(new Date() + " " + i + " `" + text + "` " + e);
            });

            timer.start();
        }
    }
}
