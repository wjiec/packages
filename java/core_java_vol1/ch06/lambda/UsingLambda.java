package lambda;

import java.util.Random;
import java.util.function.*;

public class UsingLambda {
    public static void main(String[] args) {
        repeat(3, () -> System.out.println("Hello World"));
        System.out.println();

        repeatEx(3, (i) -> System.out.println("Count down " + (2 - i)));
        System.out.println();

        getDouble(() -> new Random().nextDouble());
        System.out.println();

        getSqrt(3, (i, s) -> System.out.println("Sqrt(" + i + ") = " + s));
        System.out.println();

        map(3, (i) -> (int) Math.pow(2, i));
        System.out.println();

        map2(3, (i, pow2i) -> Math.pow(pow2i, i));
        System.out.println();

        unary1(3, i -> ~i);
        System.out.println();

        sum(3, (i, di) -> i + di);
        System.out.println();

        odd(3, i -> i % 2 == 0);
        System.out.println();
    }

    private static void repeat(int count, Runnable runnable) {
        for (int i = 0; i < count; i++) {
            runnable.run();
        }
    }

    private static void getDouble(DoubleSupplier supplier) {
        double value = supplier.getAsDouble();
        System.out.println("Print from `getDouble`, value = " + value);
    }

    private static void repeatEx(int count, IntConsumer consumer) {
        for (int i = 0; i < count; i++) {
            consumer.accept(i);
        }
    }

    private static void getSqrt(int count, ObjDoubleConsumer<Integer> consumer) {
        for (int i = 0; i < count; i++) {
            consumer.accept(i, Math.sqrt(i));
        }
    }

    private static void map(int count, IntFunction<Integer> function) {
        for (int i = 0; i < count; i++) {
            System.out.println("Print from `map`, p = " + i + " value = " + function.apply(i));
        }
    }

    private static void map2(int count, ToDoubleBiFunction<Integer, Integer> function) {
        for (int i = 0; i < count; i++) {
            System.out.printf("Print from `map2`, p1 = %d, p2 = %d, value = %f\n",
                i, i * i, function.applyAsDouble(i, i * i));
        }
    }

    private static void unary1(int count, IntUnaryOperator function) {
        for (int i = 0; i < count; i++) {
            System.out.printf("Print from `unary1`, p = %d, value = %d\n",
                i, function.applyAsInt(i));
        }
    }

    private static void sum(int count, IntBinaryOperator function) {
        for (int i = 0; i < count; i++) {
            System.out.printf("Print from `sum`, p = %d, value = %d\n",
                i, function.applyAsInt(i, i * i));
        }
    }

    private static void odd(int count, IntPredicate function) {
        for (int i = 0; i < count; i++) {
            System.out.printf("Print from `sum`, p = %d, value = %b\n",
                i, function.test(i));
        }
    }
}
