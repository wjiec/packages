public class ValueHolderSample {
    public static void main(String[] args) {
        Integer number = 1;
        System.out.println(number);

        plus(number);
        System.out.println(number);
    }

    private static void plus(Integer number) {
        number++;
    }
}
