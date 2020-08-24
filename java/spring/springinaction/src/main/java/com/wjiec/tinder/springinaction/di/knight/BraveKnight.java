package com.wjiec.tinder.springinaction.di.knight;

import com.wjiec.tinder.springinaction.di.quest.Quest;

public class BraveKnight implements Knight {

    private Quest quest;

    public BraveKnight(Quest quest) {
        this.quest = quest;
    }

    @Override
    public void embark() {
        quest.embark();
    }

}
