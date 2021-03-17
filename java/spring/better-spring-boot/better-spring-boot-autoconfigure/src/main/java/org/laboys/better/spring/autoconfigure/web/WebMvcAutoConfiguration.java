package org.laboys.better.spring.autoconfigure.web;

import org.laboys.better.spring.core.convert.StringToEnumConverterFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Servlet;

@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass({ Servlet.class, DispatcherServlet.class, WebMvcConfigurer.class })
class WebMvcAutoConfiguration implements WebMvcConfigurer {

    private final WebProperties properties;
    private final ListableBeanFactory beanFactory;

    public WebMvcAutoConfiguration(WebProperties properties, ListableBeanFactory beanFactory) {
        this.properties = properties;
        this.beanFactory = beanFactory;
    }

    /**
     * 注册类型转换器和转换器工厂
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(StringToEnumConverterFactory.builder()
            .acceptCaseInsensitiveEnums(properties.getFeature().getAcceptCaseInsensitiveEnums())
            .readUnknownEnumUsingDefaultValue(properties.getFeature().getReadUnknownEnumUsingDefaultValue())
            .build());

        for (var factory : beanFactory.getBeansOfType(ConverterFactory.class).values()) {
            registry.addConverterFactory(factory);
        }
    }

    /**
     * 注册请求拦截器和处理拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        for (var interceptor : beanFactory.getBeansOfType(HandlerInterceptor.class).values()) {
            registry.addInterceptor(interceptor);
        }
        for (var interceptor : beanFactory.getBeansOfType(WebRequestInterceptor.class).values()) {
            registry.addWebRequestInterceptor(interceptor);
        }
    }

}
