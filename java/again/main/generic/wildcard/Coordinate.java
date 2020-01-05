package main.generic.wildcard;

public class Coordinate implements Position {

    private int x;

    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int get(int index) {
        switch (index) {
            case 0:
                return x;
            case 1:
                return y;
        }

        return -1;
    }

}
