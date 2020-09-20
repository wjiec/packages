package com.wjiec.learn.effectivejava.comparable;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class EqualsAndComparable {

    public static void main(String[] args) {
        Set<BigDecimal> set1 = new HashSet<>();
        set1.add(new BigDecimal("1.0"));
        set1.add(new BigDecimal("1.00"));
        System.out.println(set1);

        Set<BigDecimal> set2 = new TreeSet<>();
        set2.add(new BigDecimal("1.0"));
        set2.add(new BigDecimal("1.00"));
        System.out.println(set2);
    }

}
