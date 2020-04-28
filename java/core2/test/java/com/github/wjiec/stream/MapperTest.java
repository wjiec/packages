package com.github.wjiec.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MapperTest {

    public static void main(String[] args) {
        Stream<String> words = Stream.of("中文龍", "English dragon", "中Java英Core混Volume排II");
        words.map(MapperTest::codePoints).forEach(Shower::show);

        words = Stream.of("中文龍", "English dragon", "中Java英Core混Volume排II");
        words.flatMap(MapperTest::codePoints).forEach(System.out::print);
    }

    private static Stream<String> codePoints(String s) {
        List<String> list = new ArrayList<>(s.length());
        for (int i = 0; i < s.length(); ) {
            int offset = s.offsetByCodePoints(i, 1);
            list.add(s.substring(i, offset));
            i = offset;
        }
        return list.stream();
    }

}
