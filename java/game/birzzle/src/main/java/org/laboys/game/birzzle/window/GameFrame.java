package org.laboys.game.birzzle.window;

import org.laboys.game.birzzle.model.Board;

import javax.swing.*;

public class GameFrame extends JFrame {

    private final int WINDOW_WIDTH = 420;
    private final int WINDOW_HEIGHT = 444;

    private final int BOARD_WIDTH = 8;
    private final int BOARD_HEIGHT = 8;

    private final TilesPanel tilesPanel;

    public GameFrame() {
        tilesPanel = new TilesPanel(new Board(BOARD_WIDTH, BOARD_HEIGHT));

        setTitle("Birzzle");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        add(tilesPanel);
    }

}
