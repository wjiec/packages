package com.wjiec.tinder.springinaction.wiring.wireway;

import com.wjiec.tinder.springinaction.wiring.model.BeanConfig;
import com.wjiec.tinder.springinaction.wiring.model.media.CompactDisc;
import com.wjiec.tinder.springinaction.wiring.model.media.SgtPeppers;
import com.wjiec.tinder.springinaction.wiring.model.player.CDPlayer;
import com.wjiec.tinder.springinaction.wiring.model.player.MediaPlayer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CodeWiringApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(BeanConfig.class);
        context.refresh();

        System.out.println(context.getBean(MediaPlayer.class));
        System.out.println(context.getBean(CDPlayer.class));
        System.out.println(context.getBean(CompactDisc.class));
        System.out.println(context.getBean(SgtPeppers.class));
//        System.out.println(context.getBean("sgtPeppers"));
        System.out.println(context.getBean("sgt"));
        System.out.println(new BeanConfig().compactDisc()); // not a bean
    }

}
