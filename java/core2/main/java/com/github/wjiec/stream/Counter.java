package com.github.wjiec.stream;

import java.util.List;
import java.util.function.Predicate;

public class Counter {

    public static <T> long count(List<T> l, Predicate<T> s) {
        return l.stream().filter(s).count();
    }

}
