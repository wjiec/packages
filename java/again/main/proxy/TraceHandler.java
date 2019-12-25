package main.proxy;

import main.reflection.Analyzer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class TraceHandler implements InvocationHandler {

    private Object target;

    public TraceHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] objects) throws Throwable {
        System.out.printf("%s.%s(%s)\n", target, method.getName(), Arrays.toString(objects));
        return method.invoke(target, objects);
    }

}
