package com.wjiec.learn.effectivejava.di;

import com.wjiec.learn.effectivejava.di.lexicon.ChineseDictionary;

public class SpellCheckTest {

    public static void main(String[] args) {
        SpellChecker checker = new SpellChecker(new ChineseDictionary());
        System.out.println(checker);
        System.out.println(checker.check("你好"));
        System.out.println(checker.check("nihao"));
        System.out.println(checker.check("world"));
        System.out.println(checker.check("世界"));
    }

}
