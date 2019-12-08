package tests.lambda;

import main.lambda.Abstract2Method;

public class Abstract2MethodTest {

    public static void main(String[] args) {
        // multiple non-overriding abstract methods found in interface main.lambda.Abstract2Method
//        testMethod((double l, double r) -> l - r);
    }

    private static void testMethod(Abstract2Method method) {
        System.out.println(method.compare(.1, .2));
    }

}
