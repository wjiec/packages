package tests.generic.wildcard;

import main.generic.erased.Pair;
import main.generic.wildcard.Coordinate;
import main.generic.wildcard.EqualCoordinate;

public class SuperTest {

    public static void main(String[] args) {
        superMethod(new Pair<>(new Coordinate(1, 1), new Coordinate(2, 2)));
    }

    private static void superMethod(Pair<? super EqualCoordinate> pos) {
        System.out.println(pos.getFirst());
        System.out.println(pos.getSecond());
    }

}
