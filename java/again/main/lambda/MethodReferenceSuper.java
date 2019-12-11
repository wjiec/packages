package main.lambda;

import javax.swing.*;

public class MethodReferenceSuper {

    public static void main(String[] args) throws InterruptedException {
        (new Derived()).hello(null);
        Thread.sleep(3000L);
    }

}

class Base {

    public void hello(Object o) {
        System.out.println("Base::hello");
    }

}

class Derived extends Base {

    @Override
    public void hello(Object o) {
        (new Timer(1000, super::hello)).start();
    }

}
