import java.util.Scanner;

public class EnumType {
    public static void main(String[] args) {
        Size shirtSize = Size.SMALL;
        System.out.printf("%s %s\n", shirtSize.hashCode(), Size.SMALL.hashCode());
        System.out.println(Size.MEDIUM.getAbbreviation());

        Scanner scanner = new Scanner(System.in);
        System.out.print("Please input a size: ");
        String input = scanner.next().toUpperCase();

        Size size = Enum.valueOf(Size.class, input);
        if (size == Size.LARGE) {
            System.out.println(size);
        }

        System.out.println(Size.EXTRA_LARGE.ordinal());

        for (Size tmpSize : Size.values()) {
            System.out.printf("%s %s %s\n", tmpSize.toString(), tmpSize.hashCode(), tmpSize.ordinal());
        }
    }
}


enum Size {
    SMALL("S"), MEDIUM("M"), LARGE("X"), EXTRA_LARGE("XL");

    private String abbreviation;

    Size(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return this.abbreviation;
    }
}
