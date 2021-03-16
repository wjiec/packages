package org.laboys.better.spring.autoconfigure.i18n;

import org.laboys.better.spring.core.i18n.Translator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class I18nAutoConfiguration {

    @Bean
    @ConditionalOnBean(MessageSource.class)
    public Translator i18nTranslator(MessageSource messageSource) {
        return new Translator(messageSource);
    }

}
