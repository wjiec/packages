package com.github.wjiec.stream;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class CounterTest {

    public static void main(String[] args) throws IOException {
        InputStream txt = CounterTest.class.getResourceAsStream("/aliceinwonderland.txt");
        String contents = new String(txt.readAllBytes(), StandardCharsets.UTF_8);
        List<String> words = List.of(contents.split("\\PL+"));

        for (int i = 0; i < 20; i++) {
            int length = i;
            System.out.printf("count for length bigger than %-2d: %d\n",
                i, Counter.count(words, (w) -> w.length() > length));
        }
    }

}
