package com.wjiec.tinder.springinaction.di;

import com.wjiec.tinder.springinaction.di.knight.BraveKnight;
import com.wjiec.tinder.springinaction.di.quest.Quest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BraveKnightTest {

    @Test
    void knightShouldEmbarkOnQuest() {
        Quest quest = Mockito.mock(Quest.class);
        BraveKnight knight = new BraveKnight(quest);
        knight.embark();

        Mockito.verify(quest, Mockito.times(1)).embark();
    }

}
