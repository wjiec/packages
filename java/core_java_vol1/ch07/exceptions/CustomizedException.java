package exceptions;

import java.io.IOException;
import java.util.Random;

public class CustomizedException {
    public static void main(String[] args) {
        try {
            someOperator();
        } catch (InternalCustomizedException e) {
            e.printStackTrace();
        }
    }

    private static void someOperator() throws InternalCustomizedException {
        if (new Random().nextInt() < 10) {
            throw new InternalCustomizedException("random number small than 10");
        }
    }

    private static class InternalCustomizedException extends IOException {

        InternalCustomizedException(String reason) {
            super(reason);
        }
    }
}
