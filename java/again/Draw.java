import java.math.BigInteger;
import java.util.Scanner;

public class Draw {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("How many numbers do you need draw: ");
        int n = scanner.nextInt();

        System.out.print("What is the highest number you can draw: ");
        int k = scanner.nextInt();

        BigInteger result = BigInteger.valueOf(1);
        for (int i = 0; i < k; ++i) {
            // result * ((n - i) / (i + 1))
            result = result.multiply(
                BigInteger.valueOf(n - i).divide(BigInteger.valueOf(i + 1))
            );
        }
        System.out.printf("Your odds are 1 in %s\n", result);
    }

}
