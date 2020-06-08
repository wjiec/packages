package com.github.wjiec.annotatin.listener;

import java.awt.*;

public class ActionListenerTest {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            var frame = new ButtonFrame();
            frame.setVisible(true);
        });
    }

}
