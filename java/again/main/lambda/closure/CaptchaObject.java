package main.lambda.closure;

import main.reflection.Analyzer;

import javax.swing.*;

public class CaptchaObject {

    private int number;

    private CaptchaObject() {
        this.number = 0;
    }

    @Override
    public String toString() {
        number += 1;
        return new Analyzer().analysis(this);
    }

    public static void main(String[] args) throws InterruptedException {
        CaptchaObject co = new CaptchaObject();
        new Timer(1000, (e) -> System.out.println(co.number)).start();
        co.number = 10;

        Thread.sleep(3000);
        co.number = 100;
        Thread.sleep(3000);
    }

}
