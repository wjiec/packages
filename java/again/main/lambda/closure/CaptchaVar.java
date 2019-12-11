package main.lambda.closure;

import javax.swing.*;

public class CaptchaVar {

    private CaptchaVar() {
        int i = 0;
        new Timer(1000, (e) -> System.out.println(i)).start();
        // java: local variables referenced from a lambda expression must be final or effectively final
        // i += 1;
    }

    public static void main(String[] args) throws InterruptedException {
        new CaptchaVar();
        Thread.sleep(3000);
    }

}
