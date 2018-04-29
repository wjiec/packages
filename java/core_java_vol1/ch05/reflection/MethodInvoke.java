package reflection;

import java.lang.reflect.Method;
import java.util.Scanner;

public class MethodInvoke {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please input a class: ");
        String className = scanner.next();

        System.out.printf("Please input a static method: ");
        String methodName = scanner.next();

        try {
            Class<?> cl = Class.forName(className);
            Method method = cl.getMethod(methodName, double.class);

            for (int i = 0; i < 10; i++) {
                Double value = (Double) method.invoke(null, (double) i);
                System.out.printf("%d -> %f\n", i, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
