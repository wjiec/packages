package tests.proxy;

import main.proxy.TraceHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class TraceHandlerTest1 {

    public static void main(String[] args) {
        Object[] numbers = new Object[1000];
        for (int i = 0; i < numbers.length; ++i) {
            InvocationHandler handler = new TraceHandler(i);
            numbers[i] = Proxy.newProxyInstance(null, new Class[] { Comparable.class }, handler);
        }

        int index = Arrays.binarySearch(numbers, 666);
        if (index >= 0) {
            System.out.println(numbers[index]);
        }
    }

}
