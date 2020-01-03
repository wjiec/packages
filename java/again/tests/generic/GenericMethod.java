package tests.generic;

import main.generic.Pair;
import main.reflection.Analyzer;

public class GenericMethod {

    public static void main(String[] args) {
        System.out.println(new Analyzer().analysis(Pair.factory("first", "second")));
//        Pair<Double> pair = Pair.<Double>factory(1, 2.33);
//        System.out.println(pair.getFirst());
    }

}
