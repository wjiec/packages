package com.wjiec.tinder.springinaction.scopebean;

import com.wjiec.tinder.springinaction.scopebean.prototypebean.Notepad;
import com.wjiec.tinder.springinaction.scopebean.prototypebean.PrototypeConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class PrototypeBeanApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(PrototypeConfig.class);
        context.refresh();

        System.out.println(context.getBean("randomNumber"));
        System.out.println(context.getBean("randomNumber"));
        System.out.println(context.getBean("randomNumber"));
        System.out.println(context.getBean(Notepad.class));
        System.out.println(context.getBean(Notepad.class));
        System.out.println(context.getBean(Notepad.class));
    }

}
