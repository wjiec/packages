package com.wjiec.springaio.javaaop.database.closer;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CloserIntroducer {

    @DeclareParents(value = "com.wjiec.springaio.javaaop.database.Database+", defaultImpl = DefaultCloserImpl.class)
    public static Closer closer;

}
