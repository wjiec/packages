package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Random;

// the same to PHP __call()
public class ProxySample {
    public static void main(String[] args) {
        Object[] numbers = new Object[1000];
        for (int i = 0; i < 1000; ++i) {
            Integer value = i;
            InvocationHandler handler = new TraceHandler(value);
            numbers[i] = Proxy.newProxyInstance(null, new Class[] {Comparable.class}, handler);
        }

        Integer key = new Random().nextInt(numbers.length) + 1;
        Arrays.binarySearch(numbers, key);
    }
}

class TraceHandler implements InvocationHandler {

    TraceHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(String.format("%s %s %s", target, method, Arrays.toString(args)));
        return method.invoke(target, args);
    }

    private Object target;
}