package com.wjiec.tinder.springinaction.wiring.model.media;

import org.springframework.stereotype.Component;

@Component
public class SgtPeppers implements CompactDisc {

    @Override
    public void play() {
        System.out.println("Playing Sgt. Pepper's Lonely Hearts Club Band by The Beatles.");
    }

}
