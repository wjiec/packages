package com.wjiec.tinder.springinaction.moreaop.critic;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CriticismEngine {

    private List<String> words;

    public CriticismEngine(List<String> words) {
        this.words = words;
    }

    public String getCriticism() {
        return words.get((int) (Math.random() * words.size()));
    }

}
