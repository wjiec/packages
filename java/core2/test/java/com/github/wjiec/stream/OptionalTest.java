package com.github.wjiec.stream;

public class OptionalTest {

    public static void main(String[] args) {
        System.out.println(Random.integer().orElse(-1));
        System.out.println(Random.integer().orElse(-1));
        System.out.println(Random.integer().orElse(-1));
        System.out.println();

        System.out.println(Random.integer().orElseGet(() -> -(int)(Math.random() * 1000)));
        System.out.println(Random.integer().orElseGet(() -> -(int)(Math.random() * 1000)));
        System.out.println(Random.integer().orElseGet(() -> -(int)(Math.random() * 1000)));
        System.out.println();

        Random.integer().ifPresent(System.out::println);
        Random.integer().ifPresent(System.out::println);
        Random.integer().ifPresent(System.out::println);
        System.out.println();

        // ofNullable(mapper)
        Random.integer().map(Random::integer).ifPresent(System.out::println);
        Random.integer().map(Random::integer).ifPresent(System.out::println);
        Random.integer().map(Random::integer).ifPresent(System.out::println);
        System.out.println();

        // mapper
        Random.integer().flatMap(Random::integer).ifPresent(System.out::println);
        Random.integer().flatMap(Random::integer).ifPresent(System.out::println);
        Random.integer().flatMap(Random::integer).ifPresent(System.out::println);
        System.out.println();
    }

}
