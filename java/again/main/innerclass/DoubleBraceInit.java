package main.innerclass;

import main.reflection.Analyzer;

import java.util.ArrayList;

public class DoubleBraceInit {

    public static void main(String[] args) {
        init(new ArrayList<>() {{ add("lisa"); add("lily"); }});
    }

    private static void init(ArrayList<String> names) {
        System.out.println(names);
        System.out.println(names.getClass().getName());
        System.out.println(new Analyzer().analysis(names));
    }

}
