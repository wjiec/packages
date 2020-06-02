package com.github.wjiec.datetime;

import com.github.wjiec.human.Person;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;

public class LocalDateTest {

    public static void main(String[] args) {
        LocalDate start = LocalDate.of(2020, Month.JANUARY, 1);
        System.out.println(start);

        LocalDate end = start.plusDays(256);
        System.out.println(end);

        start.datesUntil(end).forEach(d -> System.out.print(d + " "));
        System.out.println();

        start.datesUntil(end, Period.ofDays(2)).forEach(d -> System.out.print(d + " "));
        System.out.println();

        start.datesUntil(end, Period.ofMonths(1)).forEach(d -> System.out.print(d + " "));
        System.out.println();
    }

}
