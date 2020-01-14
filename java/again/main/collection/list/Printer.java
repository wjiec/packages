package main.collection.list;

import java.util.Collection;
import java.util.StringJoiner;

public class Printer {

    public static <T> void print(Collection<T> collection) {
        StringJoiner joiner = new StringJoiner(", ");
        for (T el : collection) {
            joiner.add(el.toString());
        }

        System.out.println(joiner);
    }

}
