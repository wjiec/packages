package com.github.wjiec.stream;

import java.util.Arrays;
import java.util.stream.Stream;

public class StreamOperatorTest {

    public static void main(String[] args) {
        Splitter.split(Arrays.asList("Hello", "12345")).forEach(System.out::println);
        Shower.show(Splitter.split(Arrays.asList("Hello", "12345")).takeWhile("0123456789"::contains));

        Splitter.split(Arrays.asList("12345", "Hello")).forEach(System.out::println);
        Shower.show(Splitter.split(Arrays.asList("12345", "Hello")).takeWhile("0123456789"::contains));

        Splitter.split(Arrays.asList("Hello", "12345")).forEach(System.out::println);
        Shower.show(Splitter.split(Arrays.asList("Hello", "12345")).dropWhile("0123456789"::contains));

        Splitter.split(Arrays.asList("12345", "Hello")).forEach(System.out::println);
        Shower.show(Splitter.split(Arrays.asList("12345", "Hello")).dropWhile("0123456789"::contains));

        Stream.concat(Splitter.split(Arrays.asList("12345", "Hello")), Splitter.split(Arrays.asList("Hello", "12345")))
            .forEach(System.out::print);

        Splitter.split(Arrays.asList("12345", "Hello")).distinct().forEach(System.out::println);
    }

}
