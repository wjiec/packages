package main.interfaces.callback;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class Clock implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        System.out.println(LocalDate.now());
    }

}
