package exceptions;

import java.io.IOException;
import java.rmi.AccessException;

public class ExceptionChain {
    public static void main(String[] args) {
        try {
            try {
                someOperator();
            } catch (IOException e) {
                throw new AccessException("exception mesage", e);
            }
        } catch (AccessException e) {
            e.printStackTrace();
        }
    }

    private static void someOperator() throws IOException {
        throw new IOException("io operator error occurs");
    }
}
