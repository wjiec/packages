package exceptions;

import java.io.IOException;

public class FinallyBlock {
    public static void main(String[] args) {
        try {
            try {
                throw new IOException("1");
            } catch (IOException e) {
                throw new IOException("2", e);
            } finally {
                System.out.println("inside finally");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("outside finally");
        }
    }
}
