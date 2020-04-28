package com.github.wjiec.stream;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Shower {

    public static <T> void show(String name, Stream<T> s, int size) {
        List<T> list = s.limit(size).collect(Collectors.toList());
        System.out.printf("%s: %s\n", name, list);
    }

    public static <T> void show(String name, Stream<T> s) {
        show(name, s, 10);
    }

    public static <T> void show(Stream<T> s) {
        show("-", s);
    }

}
