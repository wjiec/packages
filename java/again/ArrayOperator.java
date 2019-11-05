import java.util.Arrays;

public class ArrayOperator {

    public static void main(String[] args) {
        int[] numbers = {0, 1};
        int[] mapping = numbers;
        mapping[0] = 1;
        System.out.println(Arrays.toString(numbers));

        numbers = Arrays.copyOf(numbers, numbers.length * 2);
        System.out.println(Arrays.toString(numbers));

        numbers = Arrays.copyOf(new int[] {-1, 0, 1, 2}, 2);
        System.out.println(Arrays.toString(numbers));
    }

}
