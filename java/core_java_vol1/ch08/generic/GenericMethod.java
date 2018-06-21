package generic;

import java.util.ArrayList;

public class GenericMethod {
    @SafeVarargs
    private static <T> T getMiddle(T... array) {
        return array[array.length / 2];
    }

    public static void main(String[] args) {
        Integer[] numbers = new Integer[] {1, 2, 3};
        Integer middle = getMiddle(numbers);
        System.out.println(middle);
    }
}
