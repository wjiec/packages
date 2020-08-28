package com.wjiec.tinder.springinaction.wiring.model;

import com.wjiec.tinder.springinaction.wiring.model.media.CompactDisc;
import com.wjiec.tinder.springinaction.wiring.model.media.SgtPeppers;
import com.wjiec.tinder.springinaction.wiring.model.player.CDPlayer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public CDPlayer cdPlayer(CompactDisc cd) {
        System.out.println(cd);
        return new CDPlayer(cd);
    }

    @Bean("sgt")
    public CompactDisc compactDisc() {
        return new SgtPeppers();
    }

}
