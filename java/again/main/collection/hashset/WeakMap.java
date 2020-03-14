package main.collection.hashset;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.WeakHashMap;

public class WeakMap {

    public static void main(String[] args) {
        HashMap<Apple, Integer> hashMap = new HashMap<>();
        putMap(hashMap);
        System.gc();
        System.out.println(hashMap);

        WeakHashMap<Apple, Integer> weakHashMap = new WeakHashMap<>();
        putMap(weakHashMap);
        System.gc();
        System.out.println(weakHashMap);
    }

    private static void putMap(Map<? super Apple, Integer> map) {
        Apple apple = new Apple(new Random().nextInt());
        map.put(apple, new Random().nextInt());
    }

    static class Apple {

        int number;

        Apple(int n) {
            number = n;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null || obj.getClass() != Apple.class) {
                return false;
            }
            return ((Apple)obj).number == number;
        }

        @Override
        public int hashCode() {
            return number;
        }
    }

}
