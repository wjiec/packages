package com.wjiec.springaio.javaaop.database;

public interface Database {

    int execute(String sql);

    int execute(String sql, Object... args);

}
