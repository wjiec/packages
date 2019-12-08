package tests.lambda;

import main.lambda.Abstract1Method;

public class Abstract1MethodTest {

    public static void main(String[] args) {
        testMethod(((left, right) -> left - right));
    }

    private static void testMethod(Abstract1Method method) {
        System.out.println(method.compare(1, 2));
    }

}
