package org.laboys.game.birzzle.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
public class Coordinate {

    private final int x;
    private final int y;

    public Coordinate top() {
        return Coordinate.builder().x(x - 1).y(y).build();
    }

    public Coordinate bottom() {
        return Coordinate.builder().x(x + 1).y(y).build();
    }

    public Coordinate left() {
        return Coordinate.builder().x(x).y(y - 1).build();
    }

    public Coordinate right() {
        return Coordinate.builder().x(x).y(y + 1).build();
    }

    public boolean negation() {
        return x < 0 || y < 0;
    }

    public boolean outOfBound(int maxX, int maxY) {
        return x > maxX || y > maxY;
    }

    public boolean adjacent(Coordinate c) {
        return (c.x == x && Math.abs(c.y - y) <= 1) || (c.y == y && Math.abs(c.x - x) <= 1);
    }

}
