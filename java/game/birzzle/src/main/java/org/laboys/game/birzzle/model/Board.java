package org.laboys.game.birzzle.model;

import java.awt.*;
import java.util.*;

public class Board {

    private final int TILE_SIZE = 50;
    private final int CLEAR_THRESHOLD = 3;
    private final Stroke rectStroke = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

    private final HashMap<Coordinate, Tile> tiles;
    private Coordinate lastSelected;

    public Board(int width, int height) {
        tiles = new HashMap<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Coordinate coordinate = new Coordinate(x, y);

                do {
                    tiles.put(coordinate, Tile.random());
                } while (canClear(coordinate));
            }
        }
    }

    public void click(int x, int y) {
        Coordinate coordinate = Coordinate.builder().x(x / (TILE_SIZE + 1)).y(y / (TILE_SIZE + 1)).build();
        if (tiles.containsKey(coordinate)) {
            synchronized (tiles) {
                if (!tiles.get(coordinate).cleared()) {
                    if (lastSelected == null) {
                        tiles.get(coordinate).select();
                        lastSelected = coordinate;
                    } else {
                        if (!lastSelected.equals(coordinate)) {
                            if (!lastSelected.adjacent(coordinate)) {
                                tiles.get(lastSelected).resetSelected();
                                tiles.get(coordinate).select();
                                lastSelected = coordinate;
                            } else {
                                tiles.get(coordinate).resetSelected();
                                tiles.get(lastSelected).resetSelected();

                                swap(lastSelected, coordinate);
                                if (!canClear(lastSelected) && !canClear(coordinate)) {
                                    swap(lastSelected, coordinate);
                                } else {
                                    clear(lastSelected);
                                    clear(coordinate);
                                }

                                lastSelected = null;
                            }
                        }
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
        graphics.setColor(tile.getColor());
        graphics.fillRect(c.getX() * TILE_SIZE + 5, c.getY() * TILE_SIZE + 5, TILE_SIZE - 10, TILE_SIZE - 10);

        graphics.dispose();
    }

    private void swap(Coordinate l, Coordinate r) {
        Tile leftTile = tiles.get(l);
        Tile rightTile = tiles.get(r);
        if (leftTile.getColor() != rightTile.getColor()) {
            tiles.put(l, rightTile);
            tiles.put(r, leftTile);
        }
    }

    private void clear(Coordinate c) {
        Set<Coordinate> coordinates = findClearableTiles(c);
        if (coordinates.size() >= CLEAR_THRESHOLD) {
            for (var cc : coordinates) {
                tiles.get(cc).clear();
            }
        }
    }

    private boolean canClear(Coordinate c) {
        return findClearableTiles(c, CLEAR_THRESHOLD).size() >= CLEAR_THRESHOLD;
    }

    private Set<Coordinate> findClearableTiles(Coordinate start) {
        return findClearableTiles(start, tiles.size());
    }

    private Set<Coordinate> findClearableTiles(Coordinate start, int threshold) {
        Set<Coordinate> result = new HashSet<>();
        Set<Coordinate> travel = new HashSet<>();
        Queue<Coordinate> pending = new ArrayDeque<>();

        result.add(start);
        travel.add(start);
        pending.add(start);

        Color color = tiles.get(start).getColor();
        while (pending.size() > 0) {
            Coordinate item = pending.poll();
            for (var c : Arrays.asList(item.top(), item.bottom(), item.left(), item.right())) {
                if (travel.contains(c)) {
                    continue;
                }

                if (c.negation() || c.outOfBound(TILE_SIZE - 1, TILE_SIZE - 1)) {
                    continue;
                }

                travel.add(c);
                if (!tiles.containsKey(c)) {
                    continue;
                }

                if (tiles.get(c).getColor() == color) {
                    result.add(c);
                    pending.add(c);
                }

                if (result.size() >= threshold) {
                    break;
                }
            }
        }

        return result;
    }

}
