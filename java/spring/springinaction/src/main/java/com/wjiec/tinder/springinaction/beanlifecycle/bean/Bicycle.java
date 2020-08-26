package com.wjiec.tinder.springinaction.beanlifecycle.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class Bicycle implements BeanNameAware, BeanFactoryAware, ApplicationContextAware,
    BeanPostProcessor, InitializingBean, DisposableBean {

    private static final AtomicInteger ai = new AtomicInteger();

    @Override
    public void setBeanName(String name) {
        log("BeanNameAware.setBeanName(%s)", name);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        log("BeanFactoryAware.BeanFactory(%s)", beanFactory);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log("ApplicationContextAware.setApplicationContext(%s)", applicationContext);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        log("BeanPostProcessor.postProcessBeforeInitialization(%s, %s)", bean, beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log("BeanPostProcessor.postProcessAfterInitialization(%s, %s)", bean, beanName);
        return bean;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log("InitializingBean.afterPropertiesSet");
    }

    @Override
    public void destroy() throws Exception {
        log("DisposableBean.destroy");
    }

    static void log(String format, Object... args) {
        System.out.printf(ai.incrementAndGet() + ": " + format + "\n", args);
    }

}
