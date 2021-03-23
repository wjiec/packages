package org.laboys.better.spring.autoconfigure.validation;

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.AggregateResourceBundleLocator;
import org.laboys.better.spring.core.util.ResourceBundleLocatorUtil;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.MessageInterpolator;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.executable.ExecutableValidator;
import java.util.Arrays;
import java.util.List;

@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(ExecutableValidator.class)
@EnableConfigurationProperties(ValidationProperties.class)
@AutoConfigureBefore(ValidationAutoConfiguration.class)
@ConditionalOnResource(resources = "classpath:META-INF/services/javax.validation.spi.ValidationProvider")
public class SuperValidationAutoConfiguration {

    private final ValidationProperties properties;

    public SuperValidationAutoConfiguration(ValidationProperties properties) {
        this.properties = properties;
    }

    /**
     * 提供增强版的参数验证器
     */
    @Bean
    public Validator validator(MessageInterpolator messageInterpolator) {
        return Validation.byDefaultProvider()
            .configure()
            .messageInterpolator(messageInterpolator)
            .buildValidatorFactory()
            .getValidator();
    }

    /**
     * 默认资源包名字为ValidationMessages, 这里根据配置参数进行修改
     */
    @Bean
    public MessageInterpolator messageInterpolator(List<ValidationResourceBundleCustomizer> customizers) {
        ValidationResourceBundleRegistry registry = new ValidationResourceBundleRegistry();
        registry.addLocator(ResourceBundleLocatorUtil.localize("validation/messages"));
        for (var customizer : customizers) {
            customizer.customize(registry);
        }

        AggregateResourceBundleLocator locator = new AggregateResourceBundleLocator(
            Arrays.asList(properties.getMessage().getBasename()), registry::aggregate);
        return new ResourceBundleMessageInterpolator(locator);
    }

    /**
     * 支持自定义资源文件名和加载自定义资源
     *
     * 如自定义资源文件名为: validation/messages, 这回尝试加载resources目录
     * 下的/validation/messages.properties文件
     */
    @Bean
    @ConditionalOnMissingBean(ValidationResourceBundleCustomizer.class)
    public ValidationResourceBundleCustomizer validationResourceBundleCustomizer() {
        return registry -> {};
    }

}
