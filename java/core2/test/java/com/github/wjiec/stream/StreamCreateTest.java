package com.github.wjiec.stream;

import com.sun.jdi.event.StepEvent;

import java.util.Scanner;
import java.util.stream.Stream;

public class StreamCreateTest {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(StreamCreateTest.class.getResourceAsStream("/aliceinwonderland.txt"))) {
            Shower.show("scanner", scanner.tokens());
        }

        Stream<String> words = Stream.of("from", "Stream", "dot", "of");
        Shower.show("Stream.of", words);

        Stream<String> empty = Stream.empty();
        Shower.show("Stream.empty", empty);

        Stream<Double> random = Stream.generate(Math::random);
        Shower.show("Stream.generate", random);

        Stream<Integer> integer = Stream.iterate(0, (n) -> n + 1);
        Shower.show("Stream.iterate", integer);

        Stream<Stream> valid = Stream.ofNullable(integer);
        Shower.show("Stream.ofNullable", valid);

        Stream<Stream> nullable = Stream.ofNullable(null);
        Shower.show("Stream.ofNullable", nullable);
    }

}
