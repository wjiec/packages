package com.wjiec.learn.effectivejava.enumpattern;

public class OperationTest {

    public static void main(String[] args) {
        double l = Math.random();
        double r = Math.random();
        for (var op : Operation.values()) {
            System.out.printf("%f %s %f = %f\n", l, op, r, op.apply(l, r));
        }

        System.out.println(Operation.fromString("^"));
    }

}
