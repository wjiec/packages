package com.wjiec.springaio.security.access;

import org.springframework.stereotype.Component;

import java.security.Principal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Component
public class SundayChecker {

    public boolean check(Principal principal) {
        System.out.println(principal);

        return LocalDateTime.now().getDayOfWeek() == DayOfWeek.SUNDAY;
    }

}
