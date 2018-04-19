import java.util.ArrayList;

public class WrapperSample {
    public static void main(String[] args) {
        ArrayList<Integer> numbers = new ArrayList<>();

        while (numbers.size() < 100) {
            numbers.add(numbers.size());
        }

        int index1 = numbers.get(1);
        System.out.println(index1);

        Integer index2 = numbers.get(2);
        System.out.println(index2++);
        System.out.println(index2);

        Integer index3 = numbers.get(3);
        System.out.println(index3);
        System.out.println(index2.equals(index3));

        // -128 <-> 127
        Integer number1 = 1;
        Integer number2 = 1;
        System.out.println(number1 == number2);

        Integer number3 = 1000;
        Integer number4 = 1000;
        System.out.println(number3 == number4);
    }
}
