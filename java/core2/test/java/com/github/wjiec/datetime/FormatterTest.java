package com.github.wjiec.datetime;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAccessor;

public class FormatterTest {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        LocalDateTime datetime = LocalDateTime.now();
        print("BASIC_ISO_DATE", datetime);

        System.out.println();
        print("ISO_LOCAL_DATE", datetime);
        print("ISO_LOCAL_TIME", datetime);
        print("ISO_LOCAL_DATE_TIME", datetime);

        System.out.println();
        print("ISO_OFFSET_DATE", ZonedDateTime.now());
        print("ISO_OFFSET_TIME", ZonedDateTime.now());
        print("ISO_OFFSET_DATE_TIME", ZonedDateTime.now());

        System.out.println();
        print("ISO_ZONED_DATE_TIME", ZonedDateTime.now());

        System.out.println();
        print("ISO_INSTANT", ZonedDateTime.now());

        System.out.println();
        print("ISO_DATE", datetime);
        print("ISO_TIME", ZonedDateTime.now());
        print("ISO_DATE_TIME", datetime);

        System.out.println();
        print("ISO_ORDINAL_DATE", ZonedDateTime.now());

        System.out.println();
        print("ISO_WEEK_DATE", ZonedDateTime.now());

        System.out.println();
        print("RFC_1123_DATE_TIME", ZonedDateTime.now());

        System.out.println();
        print("ShortLocaleDate", DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT), datetime);
        print("ShortLocaleTime", DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT), datetime);
        print("ShortLocaleDateTime", DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT), datetime);

        System.out.println();
        print("MediumLocaleDate", DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM), datetime);
        print("MediumLocaleTime", DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM), datetime);
        print("MediumLocaleDateTime", DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM), datetime);

        System.out.println();
        print("LongLocaleDate", DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG), datetime);
        print("LongLocaleTime", DateTimeFormatter.ofLocalizedTime(FormatStyle.LONG), ZonedDateTime.now());
        print("LongLocaleDateTime", DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG), ZonedDateTime.now());

        System.out.println();
        print("FullLocaleDate", DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL), ZonedDateTime.now());
        print("FullLocaleTime", DateTimeFormatter.ofLocalizedTime(FormatStyle.FULL), ZonedDateTime.now());
        print("FullLocaleDateTime", DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL), ZonedDateTime.now());

    }

    private static void print(String name, TemporalAccessor datetime) throws NoSuchFieldException, IllegalAccessException {
        var formatter = (DateTimeFormatter)DateTimeFormatter.class.getDeclaredField(name).get(DateTimeFormatter.class);
        print(name, formatter, datetime);
    }

    private static void print(String name, DateTimeFormatter formatter, TemporalAccessor datetime) {
        System.out.printf("%s: %s\n", name, formatter.format(datetime));
        try {
            System.out.println("\t" + LocalDateTime.parse(formatter.format(datetime)));
        } catch (DateTimeParseException ignored) {}
    }

}
