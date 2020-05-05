package com.github.wjiec.stream;

import java.util.stream.Collectors;

public class BasicTypeStreamTest {

    public static void main(String[] args) {
        AliceInWonderLand.chars().limit(100).forEach(System.out::println);

        System.out.println(AliceInWonderLand.codePoints()
            .limit(200).mapToObj(c -> String.format("%X ", c)).collect(Collectors.joining()));;
    }

}
