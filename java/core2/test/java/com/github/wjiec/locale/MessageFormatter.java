package com.github.wjiec.locale;

import java.text.MessageFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MessageFormatter {

    public static void main(String[] args) {
        MessageFormat formatter = new MessageFormat("Number: {0, number, integer}, Currency: {0, number, currency}", Locale.CHINA);
        System.out.println(formatter.format(new Object[] {123456789}));

        formatter = new MessageFormat("Short: {0, date, short}, Full: {0, date, full}", Locale.CHINA);
        System.out.println(formatter.format(new Object[] {new GregorianCalendar().getTime()}));
    }

}
