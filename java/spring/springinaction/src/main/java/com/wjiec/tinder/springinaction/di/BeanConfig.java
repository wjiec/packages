package com.wjiec.tinder.springinaction.di;

import com.wjiec.tinder.springinaction.di.knight.BraveKnight;
import com.wjiec.tinder.springinaction.di.knight.Knight;
import com.wjiec.tinder.springinaction.di.quest.Quest;
import com.wjiec.tinder.springinaction.di.quest.SlayDragonQuest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public Knight knight() {
        return new BraveKnight(quest());
    }

    @Bean
    public Quest quest() {
        return new SlayDragonQuest(System.out);
    }

}
