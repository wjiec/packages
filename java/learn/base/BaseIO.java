/**
 * @package Base
 * @author ShadowMan
 */
package Base;

import java.util.Scanner;

public class BaseIO {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int ival = scanner.nextInt();
        double dval = scanner.nextDouble();
        String sval = scanner.next();
        String remaining = scanner.nextLine();

        System.out.printf("nextInt() -> %d\n", ival);
        System.out.printf("nextDouble() -> %f\n", dval);
        System.out.printf("next() -> %s\n", sval);
        System.out.printf("nextLine() -> %s\n", remaining);
    }
}
