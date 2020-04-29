package com.github.wjiec.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Splitter {

    public static Stream<String> split(String s) {
        List<String> list = new ArrayList<>(s.length());
        for (int i = 0; i < s.length(); ) {
            int offset = s.offsetByCodePoints(i, 1);
            list.add(s.substring(i, offset));
            i = offset;
        }
        return list.stream();
    }

    public static Stream<String> split(Stream<String> s) {
        return s.flatMap(Splitter::split);
    }

    public static Stream<String> split(List<String> l) {
        return split(l.stream());
    }

}
