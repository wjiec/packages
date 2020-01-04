package tests.generic.erased;

import main.generic.erased.Interval;
import main.generic.erased.Pair;

import java.time.LocalDate;

public class IntervalTest {

    public static void main(String[] args) {
        Interval interval = new Interval(LocalDate.of(2020, 1, 1), LocalDate.now());
        Pair<LocalDate> pair = interval;

        pair.setSecond(LocalDate.of(2020, 12, 31));
        System.out.println(pair.getFirst());
        System.out.println(pair.getSecond());
    }

}
