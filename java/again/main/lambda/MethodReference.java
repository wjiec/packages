package main.lambda;

import javax.swing.*;
import java.util.function.Predicate;

public class MethodReference {

    public static void main(String[] args) throws InterruptedException {
        new Timer(1000, System.out::println).start();

        Predicate<String> p = String::isEmpty;
        System.out.println(p);

        Thread.sleep(3000L);
    }

}
