package com.github.wjiec.stream;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Shower {

    public static <T> int show(String name, Stream<T> s, int size) {
        List<T> list = s.limit(size).collect(Collectors.toList());
        System.out.printf("%s: %s\n", name, list);
        return size;
    }

    public static <T> int show(String name, Stream<T> s) {
        return show(name, s, 10);
    }

    public static <T> int show(Stream<T> s) {
        return show("-", s);
    }

}
