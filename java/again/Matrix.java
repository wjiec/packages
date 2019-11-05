import java.util.Arrays;

public class Matrix {

    public static void main(String[] args) {
        int[][] matrix = new int[10][10];

        for (int i = 0; i < matrix.length; ++i) {
            matrix[0][i] = i;
            matrix[i][0] = i;
        }

        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix[i].length; ++j) {
                if (matrix[i][j] == 0) {
                    if (i != 0) {
                        matrix[i][j] += matrix[i - 1][j];
                    }
                    if (j != 0) {
                        matrix[i][j] += matrix[i][j - 1];
                    }
                }
            }
        }

        System.out.println(Arrays.deepToString(matrix));
        for (int[] row : matrix) {
            for (int number : row) {
                System.out.printf("%-8d", number);
            }
            System.out.println();
        }
    }

}
