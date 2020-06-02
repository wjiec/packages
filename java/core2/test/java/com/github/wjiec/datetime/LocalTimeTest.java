package com.github.wjiec.datetime;

import java.time.LocalTime;

public class LocalTimeTest {

    public static void main(String[] args) {
        LocalTime start = LocalTime.of(13, 14);
        System.out.println(start);

        LocalTime end = start.plusSeconds(520);
        System.out.println(end);

        System.out.println(LocalTime.now().getNano());
    }

}
