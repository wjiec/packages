package com.wjiec.learn.effectivejava.noninstantiability;

import java.util.regex.Pattern;

public class Strings {

    // To avoid instantiation or inherit
    private Strings() {}

    public static String camel(String text) {
        while (text.contains("-") || text.contains("_")) {
            text = Pattern.compile("[_\\-](\\w)").matcher(text).replaceAll((r -> r.group(1).toUpperCase()));
        }
        return text;
    }

}
