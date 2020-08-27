package com.wjiec.tinder.springinaction.di.wirng;

import com.wjiec.tinder.springinaction.wiring.model.player.MediaPlayer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CDPlayerTest {

    @Autowired
    private MediaPlayer player;

    @Test
    public void cdShouldNotBeNUll() {
    }

}
