package com.github.wjiec.security.loader;

public class SecurityClassLoader extends ClassLoader {

    private int key;

    SecurityClassLoader(int key) {
        this.key = key;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        System.out.printf("Find class : %s\n", name);
        return super.findClass(name);
    }
}
