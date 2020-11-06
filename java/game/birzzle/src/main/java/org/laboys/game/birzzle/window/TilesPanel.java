package org.laboys.game.birzzle.window;

import org.laboys.game.birzzle.model.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TilesPanel extends JPanel {

    private final Board board;

    TilesPanel(Board board) {
        this.board = board;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                board.click(e.getX(), e.getY());
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        board.draw(g);

        repaint();
    }

}
