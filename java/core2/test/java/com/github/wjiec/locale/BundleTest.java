package com.github.wjiec.locale;

import java.util.Locale;
import java.util.ResourceBundle;

public class BundleTest {

    public static void main(String[] args) {
        ResourceBundle bundle = ResourceBundle.getBundle("idea/db");
        System.out.println(bundle);
        System.out.println(bundle.getString("locale"));
        System.out.println(bundle.getString("name"));

        bundle = ResourceBundle.getBundle("idea/db", Locale.CHINA);
        System.out.println(bundle);
        System.out.println(bundle.getString("locale"));
        System.out.println(bundle.getString("name"));
    }

}
