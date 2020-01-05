package main.generic.helper;

import main.generic.erased.Pair;

public class Swap {

    public static void swap(Pair<?> p) {
        doSwap(p);
    }

    private static <T> void doSwap(Pair<T> p) {
        T tmp = p.getFirst();
        p.setFirst(p.getSecond());
        p.setSecond(tmp);
    }

}
