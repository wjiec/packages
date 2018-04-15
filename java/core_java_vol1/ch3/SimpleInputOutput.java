import java.util.Scanner;

public class SimpleInputOutput {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Hello, What your name? ");
        String name = scanner.nextLine();

        System.out.printf("Okay, %s, how old are you? ", name);
        int age = scanner.nextInt();

        System.out.printf("hi, %s, next year, you'll be %d", name, age + 1);
    }
}
