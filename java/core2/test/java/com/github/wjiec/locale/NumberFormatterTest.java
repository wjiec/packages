package com.github.wjiec.locale;

import java.text.NumberFormat;
import java.util.Locale;

public class NumberFormatterTest {

    public static void main(String[] args) {
        for (var s : Locale.getAvailableLocales()) {
            print(s, NumberFormat.getNumberInstance(s));
            print(s, NumberFormat.getCurrencyInstance(s));
        }
    }

    private static void print(Locale locale, NumberFormat formatter) {
        System.out.println(Locale.CHINA.getDisplayName(locale) + ":");
        System.out.println("\t" + formatter.format(1234567890.123));
    }

}
