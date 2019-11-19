import java.util.Arrays;
import java.util.Scanner;

public class EnumTest {

    public static void main(String[] args) {
        Size s0 = Size.SMALL;
        System.out.println(s0);
        System.out.println(s0 == Size.SMALL);

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Please enter you size: ");
            String input = scanner.next();
            Size s1 = Enum.valueOf(Size.class, input);
            System.out.println(s1.getNumber());
        } catch (Exception e) {
            System.out.printf("Chosen: %s", Arrays.toString(Size.values()));
        }
    }

}

enum Size {

    SMALL(0), MEDIUM(10), LARGE(20), EXTRA_LARGE(30);

    private int number;

    private Size(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}