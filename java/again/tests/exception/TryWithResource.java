package tests.exception;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TryWithResource {

    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) { // AutoCloseable
            while (in.hasNext()) {
                String s = in.nextLine();
                if (s.equals("asd")) {
                    break;
                }
            }
        }

        try (Scanner in = new Scanner(new FileInputStream("invalid.txt"))) {
            while (in.hasNext()) {
                System.out.println(in.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
