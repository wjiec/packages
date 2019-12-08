package tests.lambda;

import main.lambda.Abstract2MethodWithDefault1;

public class Abstract2MethodWith1DefaultTest {

    public static void main(String[] args) {
        testMethod(((left, right) -> left - right));
    }

    private static void testMethod(Abstract2MethodWithDefault1 method) {
        System.out.println(method.compare(1, 2));
        System.out.println(method.compare(.1, .2));
    }

}
