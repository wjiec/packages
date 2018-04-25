public class VariableArguments {
    public static void main(String[] args) {
        System.out.println(findMaxValue(1, 0.2, 1.3, 3.9, 9.12, 9.11));
        printObject("%s %s %s", 1, 2.33, "Hello");
        printObject("%s %s %s", new Object[] { 1, 2.33, "Hello" });
        printObject("%s %s %s", new Object[] { 1, 2.33, new Object[] { 1, 2.33, "Hello" } });
        printObject("%s %s %s", new Object[] { 1, 2.33, new Object[] { 1, 2.33, "Hello" } }, -1, Double.NEGATIVE_INFINITY);
    }

    private static double findMaxValue(double ...values) {
        double maxValue = Double.NEGATIVE_INFINITY;

        for (double value : values) {
            if (value > maxValue) {
                maxValue = value;
            }
        }

        return maxValue;
    }

    private static void printObject(String format, Object ...args) {
        System.out.printf(format + "\n", args);
    }
}
