package org.laboys.game.birzzle.model;

import lombok.Getter;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

@Getter
public class Tile {

    private static final Color SELECTED_BORDER_COLOR = Color.BLACK;
    private static final Color DEFAULT_BORDER_COLOR = Color.WHITE;
    private static final Color CLEARED_COLOR = Color.GRAY;
    private static final Color[] colors = new Color[]{
        Color.RED, Color.GREEN, Color.YELLOW, Color.CYAN, Color.PINK, Color.MAGENTA
    };

    private boolean selected;
    private Color color;

    private Tile(Color color) {
        this.selected = false;
        this.color = color;
    }

    public void select() {
        this.selected = true;
    }

    public void clear() {
        this.color = CLEARED_COLOR;
    }

    public boolean cleared() {
        return this.color == Color.GRAY;
    }

    public void resetSelected() {
        this.selected = false;
    }

    public Color borderColor() {
        if (selected) {
            return SELECTED_BORDER_COLOR;
        }
        return DEFAULT_BORDER_COLOR;
    }

    public static Tile random() {
        return new Tile(colors[ThreadLocalRandom.current().nextInt(colors.length)]);
    }

}
