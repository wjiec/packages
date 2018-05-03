package interfaces;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class TimerCallback {
    public static void main(String[] args) {
        Timer t = new Timer(1000, new TimePrinter());
        t.start();

        JOptionPane.showMessageDialog(null, "exit timer");
        System.exit(0);
    }
}


class TimePrinter implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(new Date());
    }
}