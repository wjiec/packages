import java.util.Arrays;

public class ArraySort {

    public static void main(String[] args) {
        int[] numbers = new int[50];
        for (int i = 0; i < numbers.length; ++i) {
            numbers[i] = (int)(Math.random() * 100);
        }
        System.out.println(Arrays.toString(numbers));

        Arrays.sort(numbers);
        System.out.println(Arrays.toString(numbers));

        System.out.println(Arrays.binarySearch(numbers, 10));
        System.out.println(Arrays.binarySearch(numbers, 100));
    }

}
