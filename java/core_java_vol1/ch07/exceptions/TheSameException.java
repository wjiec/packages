package exceptions;

import java.io.IOException;
import java.util.Random;

public class TheSameException {
    public static void main(String[] args) {
        try {
            operator1();
            operator2();
        } catch (IoExceptionSample1 | IoExceptionSample2 e) {
            e.printStackTrace();
        }
    }

    private static void operator1() throws IoExceptionSample1 {
        if (new Random().nextInt() < 10) {
            throw new IoExceptionSample1();
        }
    }

    private static void operator2() throws IoExceptionSample2 {
        if (new Random().nextInt() < 10) {
            throw new IoExceptionSample2();
        }
    }

    private static class IoExceptionSample1 extends IOException {
    }

    private static class IoExceptionSample2 extends IOException {
    }
}
