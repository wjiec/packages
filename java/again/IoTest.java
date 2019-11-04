import java.io.Console;
import java.util.Scanner;

public class IoTest {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("What is your name: ");
        String name = scanner.nextLine();

        System.out.print("How old are you: ");
        int age = scanner.nextInt();

        System.out.printf("Hello, %s. Next year you'll be %d\n", name, age + 1);
        if (name.equals("jayson") && age == 23) {
            Console console = System.console();
            if (console == null) {
                System.out.println("Unsupported terminal");
                return;
            }

            String username = console.readLine("Username: ");
            char[] password = console.readPassword("Password: ");

            System.out.printf("Hello agent, %s. your passcode is\n", username);
            for (char ch : password) {
                System.out.print(ch + "-");
            }
        }
    }

}
