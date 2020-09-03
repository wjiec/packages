package com.wjiec.tinder.springinaction.conditional;

import com.wjiec.tinder.springinaction.conditional.magic.MagicConfiguration;
import com.wjiec.tinder.springinaction.wiring.model.media.CompactDisc;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConditionalBeanApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(MagicConfiguration.class);
        context.refresh();

        System.out.println(context.getBean(CompactDisc.class));
    }

}
