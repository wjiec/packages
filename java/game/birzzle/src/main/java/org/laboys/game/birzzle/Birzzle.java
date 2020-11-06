package org.laboys.game.birzzle;

import org.laboys.game.birzzle.window.GameFrame;

import java.awt.*;

public class Birzzle {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new GameFrame().setVisible(true));
    }

}
