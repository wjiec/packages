package org.laboys.better.spring.autoconfigure.validation;

public interface ValidationResourceBundleCustomizer {

    /**
     * 手动注册资源定位器
     */
    void customize(ValidationResourceBundleRegistry registry);

}
