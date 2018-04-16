import java.math.BigInteger;
import java.util.Scanner;

public class BigNumber {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("How many numbers do you need to draw? ");
        int drawCount = scanner.nextInt();

        System.out.print("What is the highest number you can draw? ");
        int maxDrawNumber = scanner.nextInt();

        BigInteger value = BigInteger.valueOf(1);
        for (int i = 0; i < drawCount; ++i) {
            value = value.multiply(BigInteger.valueOf(maxDrawNumber - i));
            value = value.divide(BigInteger.valueOf(i + 1));
        }

        System.out.printf("1 / %s", value.toString());
    }
}
