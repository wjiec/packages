import java.util.Scanner;

public class ScannerSample {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int number = scanner.nextInt();
        double double_value = scanner.nextDouble();
        String s = scanner.next();

        System.out.printf("%d %f %s", number, double_value, s);
    }
}
