package exceptions;

import java.io.IOException;
import java.util.Random;

public class InternalException {
    public static void main(String[] args) {
        try {
            someOperator();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void someOperator() throws IOException {
        if (new Random().nextInt() < 10) {
            throw new IOException("random number small than 10");
        }
    }
}
