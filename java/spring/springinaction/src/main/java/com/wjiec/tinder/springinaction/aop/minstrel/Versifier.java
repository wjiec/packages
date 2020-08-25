package com.wjiec.tinder.springinaction.aop.minstrel;

import java.io.PrintStream;

public class Versifier implements Minstrel {

    private PrintStream stream;

    public Versifier(PrintStream stream) {
        this.stream = stream;
    }

    @Override
    public void singBefore() {
        stream.println("Versifier: Fa la la, the knight is so brave!");
    }

    @Override
    public void singAfter() {
        stream.println("Versifier: Tee hee hee, the brave knight did embark on a quest");
    }

}
