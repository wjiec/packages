package com.wjiec.learn.effectivejava.staticfactorymethod;

import java.util.Set;

public interface StaticFactoryMethod {

    static <T> T from(Object o) {
        return null;
    }

    static <T> Set<T> of(Object ...args) {
        return null;
    }

    static <T> T valueOf(Object ...args) {
        return null;
    }

    static <T> T instance() {
        return null;
    }

    static <T> T getInstance() {
        return null;
    }

    static <T> T create() {
        return null;
    }

    static <T> T createInstance() {
        return null;
    }

}
