package lambda;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ThisReference extends SuperClass {
    public static void main(String[] args) {
        ThisReference t = new ThisReference();
        t.startTimer();
        t.startSuperTimer();

        JOptionPane.showMessageDialog(null, "Exit ?");
        System.exit(0);
    }

    private void startTimer() {
        Timer timer = new Timer(1000, this::timerHandle);
        timer.start();
    }

    private void startSuperTimer() {
        Timer timer = new Timer(1000, super::timerHandle);
        timer.start();
    }

    @Override
    protected void timerHandle(ActionEvent e) {
        System.out.println("Hello timer " + e);
    }
}

class SuperClass {
    protected void timerHandle(ActionEvent e) {
        System.out.println("Hello timer from super " + e);
    }
}
