package com.wjiec.springaio.xmlaop.database.closer;

import org.aspectj.lang.annotation.DeclareParents;

public class CloserIntroducer {

    @DeclareParents(value = "com.wjiec.springaio.javaaop.database.Database+", defaultImpl = DefaultCloserImpl.class)
    public static Closer closer;

}
