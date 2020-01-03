package tests.generic;

import main.generic.Pair;
import main.reflection.Analyzer;

public class PairTest {

    public static void main(String[] args) {
        Pair<Integer> p0 = new Pair<>(0, 1);
        System.out.println(p0.getFirst());
        System.out.println(p0.getSecond());

        Pair<String> p1 = new Pair<>();
        p1.setFirst("hello");
        p1.setSecond("hello");
        System.out.println(new Analyzer().analysis(p1));
    }

}
