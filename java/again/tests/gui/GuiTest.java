package tests.gui;

import main.gui.SimpleFrame;

import javax.swing.*;
import java.awt.*;

public class GuiTest {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            SimpleFrame frame = new SimpleFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocation(300, 300);
            frame.setVisible(true);
        });
    }

}
