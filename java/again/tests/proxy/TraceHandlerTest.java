package tests.proxy;

import main.proxy.TraceHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class TraceHandlerTest {

    public static void main(String[] args) {
        Integer number = 5;
        InvocationHandler handler = new TraceHandler(number);
        Class[] interfaces = new Class[] { Comparable.class };
        Object proxy = Proxy.newProxyInstance(null, interfaces, handler);

        System.out.println(((Comparable<Integer>)proxy).compareTo(4));
        System.out.println(((Comparable<Integer>)proxy).compareTo(5));
        System.out.println(((Comparable<Integer>)proxy).compareTo(6));
    }

}
