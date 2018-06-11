package asserts;

import java.util.Random;

// -enableassertions OR -ea
public class AssertSample {
    public static void main(String[] args) {
        int number = new Random().nextInt(20);
        assert number > 10 : "number <= 10";

        System.out.println(number);
        System.out.println(Math.sqrt(number));
    }
}
