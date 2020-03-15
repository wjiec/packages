package main.collection.algo;

import java.util.*;
import java.util.stream.Collectors;

public class AlgoTest {

    public static void main(String[] args) {
        String[] strings = new String[64];
        for (int i = 0; i < strings.length; ++i) {
            strings[i] = Character.toString('A' + i);
        }

        List<String> list = Arrays.stream(strings).collect(Collectors.toCollection(Vector::new));
        Collections.shuffle(list);
        System.out.println(list);

        List<String> head = list.subList(0, 16);
        System.out.println(head);

        Collections.sort(head);
        System.out.println(head);
        System.out.println(list);
    }

}
