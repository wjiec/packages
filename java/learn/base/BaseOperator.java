/**
 * @package Base
 * @author ShadowMan
 */
package Base;

public class BaseOperator {
    public static void main(String[] args) {
        byte b1 = 0b01010101;
        byte b2 = (byte)0b10101010;

        System.out.printf("b1 & b2 = %d & %d = %d\n", b1, b2, b1 & b2);
        System.out.printf("b1 | b2 = %d | %d = %d\n", b1, b2, b1 | b2);
        System.out.printf("b1 ^ b2 = %d ^ %d = %d\n", b1, b2, b1 ^ b2);
        System.out.printf("~ b1 = ~ %d = %d\n", b1, ~b1);
    }
}
