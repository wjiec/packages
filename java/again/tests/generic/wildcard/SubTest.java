package tests.generic.wildcard;

import main.generic.erased.Pair;
import main.generic.wildcard.Coordinate;
import main.generic.wildcard.EqualCoordinate;

public class SubTest {

    public static void main(String[] args) {
        sub(new Pair<>(new Coordinate(5, 6), new EqualCoordinate(3)));
    }

    private static void sub(Pair<? extends Coordinate> pos) {
        System.out.println(pos.getFirst().get(0));
        System.out.println(pos.getFirst().get(1));
    }

}
