package OOP;

import java.time.LocalDate;

public class Calendar {

    public static void main(String[] args) {
        print(LocalDate.now());
    }

    public static void print(LocalDate now) {
        System.out.printf("%10s%d / %02d / %02d\n", "", now.getYear(), now.getMonthValue(), now.getDayOfMonth());
        System.out.printf("%-6s%-6s%-6s%-6s%-6s%-6s%-6s\n", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun");

        LocalDate date = now.minusDays(now.getDayOfMonth() - 1);
        for (int i = 1; i < date.getDayOfWeek().getValue(); ++i) {
            System.out.printf("%-6s", " ");
        }

        for (; date.getMonthValue() == now.getMonthValue(); date = date.plusDays(1)) {
            System.out.printf("%-6s", String.format("%s%s", date.getDayOfMonth(), date.compareTo(now) == 0 ? "*" : " "));
            if (date.getDayOfWeek().getValue() == 7) {
                System.out.println();
            }
        }
    }

}
