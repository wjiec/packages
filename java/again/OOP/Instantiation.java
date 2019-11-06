package OOP;

import java.time.LocalDate;
import java.util.Date;

public class Instantiation {

    public static void main(String[] args) {
        System.out.println(new Date());

//        Date empty; // variable might not have been initialized
//        System.out.println(empty == null);

        System.out.println(LocalDate.now());

        LocalDate date = LocalDate.of(2019, 11, 11);
        System.out.println(date.getYear());
        System.out.println(date.getMonth());
        System.out.println(date.getDayOfMonth());
        System.out.println(date.minusDays(2500));
    }

}
