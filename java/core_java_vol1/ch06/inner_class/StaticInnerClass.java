package inner_class;

import java.util.ArrayList;

public class StaticInnerClass {
    public static void main(String[] args) {
        int[] numbers = {0, -2, 3, -9, 8, 10};
        ArrayAlg.Pair pair = ArrayAlg.findInteger(numbers);

        System.out.printf("min = %d, max = %d\n", pair.getMin(), pair.getMax());
    }

    private static class ArrayAlg {

        public static Pair<Integer> findInteger(int[] array) {
            int min = Integer.MAX_VALUE;
            int max = Integer.MIN_VALUE;

            for (int val : array) {
                if (val > max) {
                    max = val;
                }

                if (val < min) {
                    min = val;
                }
            }

            return new Pair<>(min, max);
        }

        private static class Pair<T> {
            private T min;
            private T max;

            Pair(T min, T max) {
                this.min = min;
                this.max = max;
            }

            T getMin() {
                return min;
            }

            T getMax() {
                return max;
            }
        }
    }
}
