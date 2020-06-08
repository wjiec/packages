package com.github.wjiec.annotatin.listener;

import javax.swing.*;
import java.awt.*;

public class ButtonFrame extends JFrame {

    private JPanel panel;
    private JButton redButton;
    private JButton greenButton;
    private JButton blueButton;

    public ButtonFrame() {
        setSize(640, 480);

        panel = new JPanel();
        add(panel);

        redButton = new JButton("red");
        greenButton = new JButton("green");
        blueButton = new JButton("blue");

        panel.add(redButton);
        panel.add(greenButton);
        panel.add(blueButton);

        ActionListenerInstaller.install(this);
    }

    @ActionListener(source = "redButton")
    public void redBackground() {
        panel.setBackground(Color.RED);
    }

    @ActionListener(source = "greenButton")
    public void greenBackground() {
        panel.setBackground(Color.GREEN);
    }

    @ActionListener(source = "blueButton")
    public void blueBackground() {
        panel.setBackground(Color.BLUE);
    }

}
