package org.laboys.better.spring.core.i18n;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * 系统I18n转译工具
 */
@AllArgsConstructor
public class Translator {

    private final MessageSource messageSource;

    public String get(String code) {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }

    public String get(String code, Object... args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    public String get(Locale locale, String code, Object... args) {
        return messageSource.getMessage(code, args, locale);
    }

}
