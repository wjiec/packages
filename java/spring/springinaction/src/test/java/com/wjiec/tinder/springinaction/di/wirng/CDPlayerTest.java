package com.wjiec.tinder.springinaction.di.wirng;

import com.wjiec.tinder.springinaction.wiring.model.ScanConfig;
import com.wjiec.tinder.springinaction.wiring.model.player.CDPlayer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ScanConfig.class)
class CDPlayerTest {

    @Autowired
    private CDPlayer player;

    @Test
    void contextLoads() {}

    @Test
    void cdShouldNotBeNUll() {
        Assertions.assertNotNull(player);
    }

}
