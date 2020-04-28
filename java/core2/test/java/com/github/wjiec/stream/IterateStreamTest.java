package com.github.wjiec.stream;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Stream;

public class IterateStreamTest {

    public static void main(String[] args) throws IOException {
        InputStream txt = CounterTest.class.getResourceAsStream("/aliceinwonderland.txt");
        String contents = new String(txt.readAllBytes(), StandardCharsets.UTF_8);
        List<String> words = List.of(contents.split("\\PL+"));

        Stream.iterate(BigInteger.ZERO, (n) -> n.compareTo(BigInteger.valueOf(20L)) < 0, (n) -> n.add(BigInteger.ONE))
            .map((n) -> {
                System.out.println(Counter.count(words, (s) -> s.length() > n.intValue()));
                return 1;
            }).count();
    }

}
