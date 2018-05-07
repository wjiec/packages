package inner_class;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class ScopedInnerClass {
    public static void main(String[] args) {
        ScopedInnerClass innerClass = new ScopedInnerClass();
        innerClass.start(1000, true);

        JOptionPane.showMessageDialog(null, "exit?");
        System.exit(0);
    }

    private void start(int interval, boolean beep) {
        class TimerPrinter implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(new Date());

                if (beep) {
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        }

        Timer timer = new Timer(interval, new TimerPrinter());
        timer.start();
    }
}
