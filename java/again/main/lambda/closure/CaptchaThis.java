package main.lambda.closure;

import main.reflection.Analyzer;

import javax.swing.*;

public class CaptchaThis {

    private CaptchaThis() {
        new Timer(1000, (e) -> System.out.println(this)).start();
    }

    @Override
    public String toString() {
        return new Analyzer().analysis(this);
    }

    public static void main(String[] args) throws InterruptedException {
        new CaptchaThis();

        Thread.sleep(3000);
    }

}
