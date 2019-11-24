package main.reflection;

import java.lang.reflect.Array;

public class ArrayCopier {

    public static Object copyOf(Object src, int length) {
        Class cl = src.getClass();
        if (!cl.isArray()) {
            return null;
        }

        Object dst = Array.newInstance(cl.getComponentType(), length);
        System.arraycopy(src, 0, dst, 0, Math.min(length, Array.getLength(src)));
        return dst;
    }

}
