/**
 * Created by ShadowMan on 2017/4/3.
 */

public class JavaFunction {
    public static void main(String[] args) {
        System.out.println(fib((9)));
    }

    public static int fib(int startNumber) {
        if (startNumber <= 1) {
            return startNumber;
        }

        return startNumber * fib((startNumber - 1));
    }
}
