package main.collection.hashset;

import java.util.HashMap;
import java.util.Map;

public class MapTest {

    public static void main(String[] args) {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "apple");
        map.put(2, "banana");
        map.put(3, "cherry");
        map.put(4, "dew");
        map.put(5, "filbert");

        System.out.println(map);
        System.out.println(map.remove(4));
        System.out.println(map.put(3, "coconut"));
        map.forEach((k, v) -> System.out.println(k + " -> " + v));

        map.clear();
        System.out.println(map.size());
    }

}
