package org.laboys.better.spring.core.util;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Function;

public class ResourceBundleLocatorUtil {

    private ResourceBundleLocatorUtil() {}

    /**
     * 从当前包内获取资源包
     */
    public static Function<Locale, ResourceBundle> localize(String basename) {
        return (locale) -> ResourceBundle.getBundle(basename, locale, ClassLoader.getSystemClassLoader());
    }

}
