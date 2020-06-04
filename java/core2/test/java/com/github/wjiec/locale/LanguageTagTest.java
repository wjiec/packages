package com.github.wjiec.locale;

import java.util.Arrays;
import java.util.Locale;

public class LanguageTagTest {

    public static void main(String[] args) {
        System.out.println(Locale.CHINA);
        System.out.println(Locale.forLanguageTag("zh-CN"));
        System.out.println(Arrays.toString(Locale.getAvailableLocales()));
        System.out.println(Arrays.toString(Locale.getISOCountries()));

        System.out.println(Locale.CHINA.getDisplayName(Locale.CHINA));
        System.out.println(Locale.US.getDisplayName(Locale.US));
    }

}
