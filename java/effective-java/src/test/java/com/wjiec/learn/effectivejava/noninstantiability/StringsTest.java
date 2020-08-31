package com.wjiec.learn.effectivejava.noninstantiability;

public class StringsTest {

    public static void main(String[] args) {
        System.out.println(Strings.camel("a_b_c"));
        System.out.println(Strings.camel("a_b-c"));
        System.out.println(Strings.camel("a-b_c"));
        System.out.println(Strings.camel("a-b-c"));
        System.out.println(Strings.camel("hello-world"));
    }

}
