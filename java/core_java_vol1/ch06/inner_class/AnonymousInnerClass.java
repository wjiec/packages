package inner_class;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class AnonymousInnerClass {
    public static void main(String[] args) {
        AnonymousInnerClass innerClass = new AnonymousInnerClass();

        innerClass.start(1000, true);

        JOptionPane.showMessageDialog(null, "Exit?");
        System.exit(0);
    }

    private void start(int interval, boolean beep) {
        Timer timer = new Timer(interval, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(new Date());

                if (beep) {
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        });

        timer.start();
    }
}
