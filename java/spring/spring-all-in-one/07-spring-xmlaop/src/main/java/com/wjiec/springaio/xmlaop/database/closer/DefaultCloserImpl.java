package com.wjiec.springaio.xmlaop.database.closer;

public class DefaultCloserImpl implements Closer {

    @Override
    public void close() {
        System.out.printf("Close: %s", getClass().getName());
    }

}
