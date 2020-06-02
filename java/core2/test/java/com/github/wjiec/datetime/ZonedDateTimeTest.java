package com.github.wjiec.datetime;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ZonedDateTimeTest {

    public static void main(String[] args) {
        System.out.println(ZoneId.getAvailableZoneIds());
        System.out.println(ZoneId.systemDefault());

        ZonedDateTime datetime = ZonedDateTime.now();
        System.out.println(datetime);

        Instant instant = datetime.toInstant();
        System.out.println(instant.atZone(ZoneId.of("UTC+0")));
        System.out.println(instant.atZone(ZoneId.of("UTC+1")));
        System.out.println(instant.atZone(ZoneId.of("UTC+2")));
        System.out.println(instant.atZone(ZoneId.of("UTC+3")));
        System.out.println(instant.atZone(ZoneId.of("UTC+4")));
        System.out.println(instant.atZone(ZoneId.of("UTC+5")));
        System.out.println(instant.atZone(ZoneId.of("UTC+6")));
        System.out.println(instant.atZone(ZoneId.of("UTC+7")));
        System.out.println(instant.atZone(ZoneId.of("UTC+8")));
    }

}
