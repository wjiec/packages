package com.github.wjiec.datetime;

import java.time.Duration;
import java.time.Instant;

public class InstantTest {

    public static void main(String[] args) {
        System.out.println(Instant.MIN);
        System.out.println(Instant.now());
        System.out.println(Instant.MAX);

        System.out.println(Duration.between(Instant.MAX, Instant.MIN));

        Instant now = Instant.now();
        Instant end = now.plusSeconds(86400 * 30);
        System.out.println(end);

        System.out.println(Duration.between(end, now));
        System.out.println(Duration.between(now, end));
        System.out.println(Duration.between(now, end).toSeconds());
        System.out.println(Duration.between(now, end).toDays());
        System.out.println(Duration.between(now, end).toMinutes());
    }

}
