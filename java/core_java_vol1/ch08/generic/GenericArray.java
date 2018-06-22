package generic;

import java.util.Arrays;

public class GenericArray {
    public static void main(String[] args) {
        @SuppressWarnings("unchecked")
        PairClass<Integer, Integer>[] array = (PairClass<Integer, Integer>[]) new PairClass<?, ?>[10];
        System.out.println(Arrays.toString(array));
    }
}
