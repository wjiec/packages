package com.github.wjiec.locale;

import java.text.Collator;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

public class SortTest {

    public static void main(String[] args) {
        Collator collator = Collator.getInstance(Locale.CHINA);
        String[] words = new String[] {"一", "三", "六", "二", "五", "四"};

        collator.setStrength(Collator.PRIMARY);
        Arrays.sort(words, collator);
        System.out.println(Arrays.toString(words));

        collator.setStrength(Collator.SECONDARY);
        Arrays.sort(words, collator);
        System.out.println(Arrays.toString(words));

        collator.setStrength(Collator.TERTIARY);
        Arrays.sort(words, collator);
        System.out.println(Arrays.toString(words));

        collator.setStrength(Collator.IDENTICAL);
        Arrays.sort(words, collator);
        System.out.println(Arrays.toString(words));
    }

}
