import java.util.Arrays;

public class ArrayOperator {
    public static void main(String[] args) {
        int[] numbers = new int[16];
        for (int i = 0; i < 16; ++i) {
            numbers[i] = i;
        }
        for (int number : numbers) {
            System.out.print(number);
        }
        System.out.println();

        for (int number : new int[] { 1, 3, 5, 7, 9 }) {
            System.out.print(number);
        }
        System.out.println();

        int[] arrayAlias = numbers;
        arrayAlias[0] = 1;
        System.out.print(Arrays.toString(numbers));
        numbers[1] = 0;
        System.out.println(Arrays.toString(arrayAlias));

        int[] copiedArray = Arrays.copyOf(arrayAlias, 2 * arrayAlias.length);
        System.out.println(Arrays.toString(copiedArray));

        int[][] matrix = {
            {1, 3, 5, 7, 9},
            {2, 4, 6, 8, 10},
            {0, 0, 0, 0, 0}
        };
        System.out.println(Arrays.deepToString(matrix));

        int [][] odds = new int[6][];
        for (int i = 0; i < odds.length; ++i) {
            odds[i] = new int[i + 1];
        }
        odds[0][0] = 1;
        for (int i = 1; i < odds.length; ++i) {
            for (int j = 0; j < odds[i].length; ++j) {
                int top = 0;
                if (i - 1 >= 0 && i - 1 < odds.length && j < odds[i - 1].length) {
                    top = odds[i - 1][j];
                }

                int left = 0;
                if (i - 1 >= 0 && i - 1 < odds.length && j - 1 >= 0 && j - 1 < odds[i - 1].length) {
                    left = odds[i - 1][j - 1];
                }

                odds[i][j] = top + left;
            }
        }
        for (int[] row : odds) {
            for (int number : row) {
                System.out.printf("%d ", number);
            }
            System.out.println();
        }
    }
}
