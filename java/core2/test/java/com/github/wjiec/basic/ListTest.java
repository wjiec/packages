package com.github.wjiec.basic;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ListTest {

    public static void main(String[] args) {
        List<Integer> l0 = Arrays.stream(new Integer[100]).collect(Collectors.toList());
        System.out.println(l0.get(0));
        System.out.println(l0.get(50));
        System.out.println(l0.get(99));
    }

}
