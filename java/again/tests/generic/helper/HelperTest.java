package tests.generic.helper;

import main.generic.erased.Pair;
import main.generic.helper.Swap;

public class HelperTest {

    public static void main(String[] args) {
        Pair<Integer> pair = new Pair<>(0, 1);

        System.out.println(pair.getFirst());
        System.out.println(pair.getSecond());

        Swap.swap(pair);
        System.out.println(pair.getFirst());
        System.out.println(pair.getSecond());
    }

}
