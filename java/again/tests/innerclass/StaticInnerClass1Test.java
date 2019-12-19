package tests.innerclass;

import main.innerclass.StaticInnerClass1;

public class StaticInnerClass1Test {

    public static void main(String[] args) {
        StaticInnerClass1.Pair pair = new StaticInnerClass1.Pair(1, 2);
        System.out.println(pair.getFirst());
        System.out.println(pair.getSecond());
    }

}
