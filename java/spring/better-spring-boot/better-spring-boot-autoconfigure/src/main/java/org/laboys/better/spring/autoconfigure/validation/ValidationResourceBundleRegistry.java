package org.laboys.better.spring.autoconfigure.validation;

import org.hibernate.validator.resourceloading.AggregateResourceBundleLocator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Function;

public class ValidationResourceBundleRegistry {

    private final List<Function<Locale, ResourceBundle>> locators = new ArrayList<>();

    public void addLocator(Function<Locale, ResourceBundle> locator) {
        locators.add(locator);
    }

    ResourceBundle aggregate(Locale locale) {
        List<ResourceBundle> rbs = new ArrayList<>();
        locators.forEach((locator) -> {
            try {
                ResourceBundle rb = locator.apply(locale);
                if (rb != null) {
                    rbs.add(rb);
                }
            } catch (Throwable ex) {
                System.out.println(ex);
            }
        });

        return new AggregateResourceBundleLocator.AggregateBundle(rbs);
    }

}
