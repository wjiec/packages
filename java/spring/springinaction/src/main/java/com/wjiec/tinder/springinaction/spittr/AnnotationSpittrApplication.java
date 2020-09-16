package com.wjiec.tinder.springinaction.spittr;

import com.wjiec.tinder.springinaction.spittr.config.SpittrWebAppInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class AnnotationSpittrApplication {

    public static void main(String[] args) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(SpittrWebAppInitializer.class);
        context.refresh();
    }

}
