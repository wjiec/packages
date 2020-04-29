package com.github.wjiec.stream;

import java.util.Optional;
import java.util.stream.Stream;

public class Random {

    public static Optional<Integer> integer() {
        if (Math.random() < 0.5) {
            return Optional.empty();
        }
        return Optional.of((int)(Math.random() * 1000));
    }

    public static Stream<Optional<Integer>> asStream() {
        return Stream.generate(Random::integer);
    }

    public static Optional<Integer> integer(Integer n) {
        if (n % 2 == 0) {
            return Optional.empty();
        }
        return Optional.of((int)(Math.random() * 1000));
    }

}
