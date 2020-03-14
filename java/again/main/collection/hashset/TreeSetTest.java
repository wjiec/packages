package main.collection.hashset;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class TreeSetTest {

    public static void main(String[] args) {
        // anonymous
//        Set<Apple> apples = new TreeSet<>(new Comparator<Apple>() {
//            @Override
//            public int compare(Apple l, Apple r) {
//                return l.number - r.number;
//            }
//        });

        // lambda
//        Set<Apple> apples = new TreeSet<>((l, r) -> l.number - r.number);

        // function
        Set<Apple> apples = new TreeSet<>(Comparator.comparingInt(l -> l.number));
        apples.add(new Apple(1));
        apples.add(new Apple(-1));
        apples.add(new Apple(3));
        apples.add(new Apple(2));
        apples.add(new Apple(-3));

        for (Apple a : apples) {
            System.out.println(a.number);
        }
    }

    static class Apple  {

        int number;

        Apple(int number) {
            this.number = number;
        }

    }

}
