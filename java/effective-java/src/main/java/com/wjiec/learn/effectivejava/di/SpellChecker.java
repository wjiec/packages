package com.wjiec.learn.effectivejava.di;

import com.wjiec.learn.effectivejava.di.lexicon.Dictionary;

public class SpellChecker {

    private final Dictionary dictionary;

    public SpellChecker(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public boolean check(String text) {
        return dictionary.contains(text);
    }

}
