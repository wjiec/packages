package OOP;

import main.reflection.Analyzer;

public class Overloading {

    public void method(String s) {}

    protected void method(int i) {}

    public static void main(String[] args) {
        System.out.println(new Analyzer().analysis(new Overloading()));
    }

}
