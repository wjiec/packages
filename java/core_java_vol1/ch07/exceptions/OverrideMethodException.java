package exceptions;

import java.io.IOException;

public class OverrideMethodException {
    public static void main(String[] args) {
        BaseClass object = new DeriveClass();
        try {
            System.out.println(object.add(1, 2));
            System.out.println(object.multiply(1, 3));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class BaseClass {
        public int add(int a, int b) throws IOException {
            if (a < 0 || b < 0) {
                throw new IOException();
            }

            return a + b;
        }

        public int multiply(int a, int b) {
            return a * b;
        }
    }

    private static class DeriveClass extends BaseClass {
        @Override
        public int add(int a, int b) throws IOException {
            return super.add(a + 10, b + 10);
        }

        @Override
        public int multiply(int a, int b) /* throws IOException */ {
            /* throw new IOException(); */
            return super.multiply(a, b);
        }
    }
}
