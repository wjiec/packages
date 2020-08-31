package com.wjiec.learn.effectivejava.di.lexicon;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ChineseDictionary implements Dictionary {

    private static final Set<String> words = new HashSet<>(Arrays.asList("你好", "世界"));

    @Override
    public boolean contains(String text) {
        return words.contains(text);
    }

}
