package com.github.wjiec.nativec;

public class NativeCTest {

    static {
        System.loadLibrary("NativeC");
    }

    public static void main(String[] args) {
        new NativeC().say();
        new NativeC().say("hello");
    }

}
