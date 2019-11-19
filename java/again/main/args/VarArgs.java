package main.args;

public class VarArgs {

    public static void main(String[] args) {
        System.out.println(sum(1, 2.33, 4.55, 123L));
        System.out.println(sum(new Number[] {1, 2.33, 4.55, 123L}));
    }

    private static double sum(Number... numbers) {
        double sum = 0;
        for (Number number : numbers) {
            sum += number.doubleValue();
        }
        return sum;
    }

}
