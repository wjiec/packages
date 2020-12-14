package com.wjiec.springaio.xmlaop.database;

public interface Database {

    int execute(String sql);

    int execute(String sql, Object... args);

}
