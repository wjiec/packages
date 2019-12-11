package main.lambda.closure;

import javax.swing.*;

public class Captcha {

    private Captcha(String s) {
        new Timer(1000, (e) -> System.out.println(s)).start();
    }

    public static void main(String[] args) throws InterruptedException {
        new Captcha("from main");
        Thread.sleep(3000);
    }

}
