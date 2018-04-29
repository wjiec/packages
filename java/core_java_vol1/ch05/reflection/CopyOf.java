package reflection;

import jdk.nashorn.internal.ir.annotations.Ignore;

import java.lang.reflect.Array;

public class CopyOf {
    public static void main(String[] args) {
        int[] numbers = {1, 3, 5, 7, 9, 2, 4, 6, 8};
        System.out.println(new ObjectAnalyzer().toString(numbers));

        int[] newNumbers1 = (int[]) copyOf(numbers, numbers.length / 2);
        System.out.println(new ObjectAnalyzer().toString(newNumbers1));

        int[] newNumbers2 = (int[]) copyOf(numbers, numbers.length * 2);
        System.out.println(new ObjectAnalyzer().toString(newNumbers2));
    }

    static Object copyOf(Object array, int length) {
        Class cl = array.getClass();

        if (!cl.isArray()) {
            return null;
        }

        Class componentType = cl.getComponentType();
        Object newArray = Array.newInstance(componentType, length);
        System.arraycopy(array, 0, newArray, 0, Math.min(length, Array.getLength(array)));

        return newArray;
    }
}
