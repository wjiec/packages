import java.time.LocalDate;
import java.util.Date;

public class ClassAndObject {
    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(date);

        String dateString = date.toString();
        System.out.println(dateString);

        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);

        System.out.println(localDate.atTime(12, 59, 59));
        System.out.println(localDate.getMonth());
        System.out.println(localDate.getMonth().name());
        System.out.println(localDate.getMonth().getValue());
    }
}
