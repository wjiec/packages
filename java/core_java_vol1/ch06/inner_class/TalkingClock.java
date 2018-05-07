package inner_class;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class TalkingClock {
    private int interval;

    private boolean beep;

    private TalkingClock(int interval, boolean beep) {
        this.interval = interval;
        this.beep = beep;
    }

    private void start() {
        Timer timer = new Timer(interval, new ClockPrinter());
        timer.start();
    }

    private class ClockPrinter implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(new Date());

            if (beep) {
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }

    public static void main(String[] args) {
        TalkingClock clock = new TalkingClock(1000, true);
        clock.start();

        JOptionPane.showMessageDialog(null, "Exit");
        System.exit(0);
    }
}
