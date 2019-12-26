package main.exception;

import java.io.IOException;
import java.util.Random;

public class ExceptionDeclare {

    private ExceptionDeclare() throws IOException {
        if (new Random().nextInt(2) == 1) {
            throw new IOException("random trigger");
        }
    }

    public static void main(String[] args) {
        try {
            new ExceptionDeclare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
