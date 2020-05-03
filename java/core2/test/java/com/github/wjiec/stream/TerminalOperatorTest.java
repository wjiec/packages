package com.github.wjiec.stream;

import java.util.Comparator;

public class TerminalOperatorTest {

    public static void main(String[] args) {
        System.out.println(AliceInWonderLand.words()
            .filter(s -> s.length() != 0)
            .min(Comparator.comparing(String::length)));

        System.out.println(AliceInWonderLand.words()
            .filter(s -> s.length() != 0)
            .max(Comparator.comparing(String::length)));

        System.out.println(AliceInWonderLand.words()
            .filter(s -> s.startsWith("Q"))
            .findFirst());

        System.out.println(AliceInWonderLand.words()
            .filter(s -> s.startsWith("Q"))
            .findAny());
    }

}
