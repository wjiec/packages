package com.wjiec.tinder.springinaction.beanlifecycle;

import com.wjiec.tinder.springinaction.beanlifecycle.bean.Bicycle;
import com.wjiec.tinder.springinaction.beanlifecycle.bean.BicycleHook;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

public class BeanLifeCycleApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan(BeanLifeCycleApplication.class.getPackageName());
        context.register(BeanLifeCycleApplication.class);
        context.refresh();

        Bicycle scanned = (Bicycle) context.getBean("bicycle");
        System.out.println(scanned);

        BicycleHook named = (BicycleHook) context.getBean("namedBicycle");
        System.out.println(named);

        context.removeBeanDefinition("bicycle");
        context.removeBeanDefinition("namedBicycle");
    }

    @Bean(initMethod = "initHook", destroyMethod = "destroyHook")
    public BicycleHook namedBicycle() {
        return new BicycleHook();
    }

}
