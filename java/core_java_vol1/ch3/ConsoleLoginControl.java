import java.io.Console;


public class ConsoleLoginControl {
    public static void main(String[] args) {
        Console console = System.console();

        String username = console.readLine("UserName: ");
        char[] password = console.readPassword("Password for %s: ", username);

        System.out.printf("Validate %s:%s\n", username, new String(password));
    }
}
