package org.laboys.game.birzzle.model;

import java.awt.*;
import java.util.HashMap;

public class Board {

    private final int TILE_SIZE = 50;
    private final Stroke rectStroke = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

    private final HashMap<Coordinate, Tile> tiles;
    private Coordinate lastSelected;

    public Board(int width, int height) {
        tiles = new HashMap<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Coordinate coordinate = new Coordinate(x, y);
                tiles.put(coordinate, Tile.random());
            }
        }
    }

    public void click(int x, int y) {
        Coordinate coordinate = Coordinate.builder().x(x / (TILE_SIZE + 1)).y(y / (TILE_SIZE + 1)).build();
        if (tiles.containsKey(coordinate)) {
            synchronized (tiles) {
                if (lastSelected == null) {
                    tiles.get(coordinate).select();
                    lastSelected = coordinate;
                } else {
                    if (!lastSelected.adjacent(coordinate)) {
                        tiles.get(lastSelected).resetSelected();
                        tiles.get(coordinate).select();
                        lastSelected = coordinate;
                    } else {
                        tiles.get(coordinate).resetSelected();
                        tiles.get(lastSelected).resetSelected();

                        swap(lastSelected, coordinate);
                        lastSelected = null;
                    }
                }
            }
        }
    }

    public void draw(Graphics g) {
        for (var el : tiles.entrySet()) {
            draw(g, el.getKey(), el.getValue());
        }
    }

    private void draw(Graphics g, Coordinate c, Tile tile) {
        Graphics2D graphics = (Graphics2D) g.create();

        graphics.setColor(tile.borderColor());
        graphics.setStroke(rectStroke);
        graphics.drawRect(c.getX() * TILE_SIZE + 2, c.getY() * TILE_SIZE + 2, TILE_SIZE - 4, TILE_SIZE - 4);

        graphics.setStroke(new BasicStroke());
        graphics.setColor(tile.getBackgroundColor());
        graphics.fillRect(c.getX() * TILE_SIZE + 5, c.getY() * TILE_SIZE + 5, TILE_SIZE - 10, TILE_SIZE - 10);

        graphics.dispose();
    }

    private void swap(Coordinate l, Coordinate r) {
        Tile t = tiles.get(l);

        tiles.put(l, tiles.get(r));
        tiles.put(r, t);
    }

}
