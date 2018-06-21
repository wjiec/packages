package generic;

public class GenericTypeLimit {

    private static <T extends Comparable<T>> T min(T[] array) {
        if (array == null || array.length == 0) {
            return null;
        }

        T smallest = array[0];
        for (int i = 1; i < array.length; ++i) {
            if (array[i].compareTo(smallest) < 0) {
                smallest = array[i];
            }
        }

        return smallest;
    }

    public static void main(String[] args) {
        int minValue = min(new Integer[] {1, 3, 2, -1, 4, 9, -5});
        System.out.println(minValue);
    }
}
