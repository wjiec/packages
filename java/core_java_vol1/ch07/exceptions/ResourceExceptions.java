package exceptions;

import java.io.FileInputStream;
        import java.io.FileNotFoundException;
        import java.util.Scanner;

public class ResourceExceptions {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(new FileInputStream("./ResourceExceptions.java"), "utf-8")) {
            while (scanner.hasNext()) {
                System.out.println(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
