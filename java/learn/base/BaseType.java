/**
 * @pakcage Base
 * @author ShadowMan
 */
package Base;

public class BaseType {
    public static void main(String[] args) {
        byte byte_val_1 = 127;
        byte byte_val_2 = -128;
        System.out.printf("byte(%d) + 1 = %d, %s\n", byte_val_1, byte_val_1 + 1, typeof((byte_val_1 + 1)));
        System.out.printf("byte(%d) - 1 = %d, %s\n\n", byte_val_2, byte_val_2 - 1, typeof((byte_val_2 - 1)));


        short short_val_1 = 1 << 15 - 1;
        short short_val_2 = -(1 << 15);
        System.out.printf("short(%d) + 1 = %d, %s\n", short_val_1, short_val_1 + 1, typeof((short_val_1 + 1)));
        System.out.printf("short(%d) - 1 = %d, %s\n\n", short_val_2, short_val_2 - 1, typeof((short_val_2 - 1)));


        int int_val_1 = (1 << 31) - 1;
        int int_val_2 = -(1 << 31);
        System.out.printf("int(%d) + 1 = %d, %s\n", int_val_1, int_val_1 + 1, typeof((int_val_1 + 1)));
        System.out.printf("int(%d) + 2 = %d, %s\n", int_val_1, int_val_1 + 2, typeof((int_val_1 + 2)));
        System.out.printf("int(%d) - 1 = %d, %s\n", int_val_2, int_val_2 - 1, typeof((int_val_2 - 1)));
        System.out.printf("int(%d) - 2 = %d, %s\n\n", int_val_2, int_val_2 - 2, typeof((int_val_2 - 2)));

        long long_val_1 = 9223372000000000000L;
        long long_val_2 = -(1 << 63);
        System.out.printf("long(%d) + 1 = %d, %s\n", long_val_1, long_val_1 + 1, typeof((long_val_1 + 1)));
        System.out.printf("long(%d) + 2 = %d, %s\n", long_val_1, long_val_1 + 2, typeof((long_val_1 + 2)));
        System.out.printf("long(%d) - 1 = %d, %s\n", long_val_2, long_val_2 - 1, typeof((long_val_2 - 1)));
        System.out.printf("long(%d) - 2 = %d, %s\n\n", long_val_2, long_val_2 - 2, typeof((long_val_2 - 2)));


        float float_val = 2.333333333333333f;
        System.out.printf("float float_val = %f, %s\n", float_val, typeof(float_val));


        double double_val = 2.33333333333333333;
        System.out.printf("doubel doubel_val = %f, %s\n", double_val, typeof(double_val));
    }

    private static String typeof(Object obj) {
        return obj.getClass().toString();
    }
}
