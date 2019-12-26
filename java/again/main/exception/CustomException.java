package main.exception;

import java.util.Random;

public class CustomException extends Exception {

    public CustomException() {}

    public CustomException(String message) {
        super(message);
    }

    public static void main(String[] args) {
        try {
            if (new Random().nextInt(2) == 1) {
                throw new CustomException("Custom exception");
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
