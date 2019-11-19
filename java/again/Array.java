import java.util.Arrays;

public class Array {

    public static void main(String[] args) {
        String[] strings = new String[16];
        System.out.println(Arrays.toString(strings));
        System.out.println(strings[12] == null);

        for (String s : strings) {
            s += "hello";
        }
        System.out.println(Arrays.toString(strings));

        int[] primes = {2, 3, 5, 7, 11, 13};
        System.out.println(Arrays.toString(primes));

        // anonymous array
        for (int n : new int[] {1, 2, 3, 4, 5}) {
            System.out.print(n + " ");
        }
        System.out.println();

        int[] numbers = {0, 1};
        System.out.println(Arrays.toString(numbers));
        numbers = new int[] {-1, 0, 1};
        System.out.println(Arrays.toString(numbers));

        int[] empty = {};
        System.out.println(Arrays.toString(empty));
        System.out.println(empty == null);

//        int[] negIndex = {0, 1, 2, 3};
//        System.out.println(negIndex[-1]);
    }

}
