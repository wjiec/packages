import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

public class CalendarTest {
    public static void main(String[] args) {
        String format = "%5s %5s %5s %5s %5s %5s %5s\n";

        // table header
        System.out.printf(format, "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun");

        // current date
        LocalDate localDate = LocalDate.now();
//        LocalDate localDate = LocalDate.of(2016, 2, 16);
        Month month = localDate.getMonth();
        int today = localDate.getDayOfMonth();

        // reset to first day of month
        localDate = localDate.minusDays(today - 1);

        // calc week start
        DayOfWeek weekStart = localDate.getDayOfWeek();
        for (int i = 1; i < weekStart.getValue(); ++i) {
            System.out.printf("%5s ", "");
            if (i == 7) {
                System.out.println();
            }
        }

        // fill numbers
        while (localDate.getMonth() == month) {
            int displayDay = localDate.getDayOfMonth();
            System.out.printf("%4d%c ", displayDay, displayDay == today ? '*' : ' ');
            if (localDate.getDayOfWeek().getValue() == 7) {
                System.out.println();
            }

            localDate = localDate.plusDays(1);
        }
    }
}
