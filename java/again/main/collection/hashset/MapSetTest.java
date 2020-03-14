package main.collection.hashset;

import java.util.*;

public class MapSetTest {

    public static void main(String[] args) {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "apple");
        map.put(2, "banana");
        map.put(3, "cherry");
        map.put(4, "dew");
        map.put(5, "filbert");

        Set<Integer> keys = map.keySet();
        for (int i : keys) {
            System.out.println(i);
        }

        StringJoiner joiner = new StringJoiner(",");
        Collection<String> values = map.values();
        values.forEach(joiner::add);
        System.out.println(joiner);
    }

}
