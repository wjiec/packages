package com.wjiec.tinder.springinaction.scopebean;

import com.wjiec.tinder.springinaction.scopebean.prototypebean.Notepad;
import com.wjiec.tinder.springinaction.scopebean.prototypebean.PrototypeConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XmlPrototypeBeanApplication {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext();
        context.setConfigLocation("classpath:scopebean/prototype-bean.xml");
        context.refresh();

        System.out.println(context.getBean(Notepad.class));
        System.out.println(context.getBean(Notepad.class));
        System.out.println(context.getBean(Notepad.class));
        System.out.println(context.getBean("randomNumber"));
        System.out.println(context.getBean("randomNumber"));
        System.out.println(context.getBean("randomNumber"));
    }

}
