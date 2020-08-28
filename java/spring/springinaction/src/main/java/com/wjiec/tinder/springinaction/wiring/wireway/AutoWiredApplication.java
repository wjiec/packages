package com.wjiec.tinder.springinaction.wiring.wireway;

import com.wjiec.tinder.springinaction.wiring.model.ScanConfig;
import com.wjiec.tinder.springinaction.wiring.model.player.MediaPlayer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AutoWiredApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ScanConfig.class);
        context.refresh();

        System.out.println(context.getBean(MediaPlayer.class));
    }

}
