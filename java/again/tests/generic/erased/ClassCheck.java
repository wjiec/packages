package tests.generic.erased;

import main.generic.erased.Pair;

public class ClassCheck {

    public static void main(String[] args) {
        Pair<Double> p0 = new Pair<>(1., 2.);
        Pair<Integer> p1 = new Pair<>(1, 2);

        System.out.println(p0.getClass() == p1.getClass());
        System.out.println(p0.getClass() == Pair.class);
        System.out.println(p1.getClass() == Pair.class);
    }

}
