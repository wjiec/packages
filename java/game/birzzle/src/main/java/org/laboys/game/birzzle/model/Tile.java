package org.laboys.game.birzzle.model;

import lombok.Getter;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

@Getter
public class Tile {

    private boolean selected;
    private Color backgroundColor;

    private Tile(Color color) {
        this.selected = false;
        this.backgroundColor = color;
    }

    public boolean selected() {
        return this.selected;
    }

    public void select() {
        this.selected = true;
    }

    public void resetSelected() {
        this.selected = false;
    }

    public Color borderColor() {
        if (selected) {
            return Color.BLACK;
        }
        return Color.WHITE;
    }

    public static Tile random() {
        return new Tile(colors[ThreadLocalRandom.current().nextInt(colors.length)]);
    }

    private static final Color[] colors = new Color[]{
        Color.RED, Color.GREEN, Color.YELLOW, Color.CYAN, Color.PINK
    };

}
