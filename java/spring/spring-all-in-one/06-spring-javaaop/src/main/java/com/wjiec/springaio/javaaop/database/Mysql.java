package com.wjiec.springaio.javaaop.database;

import org.springframework.stereotype.Component;

@Component
public class Mysql implements Database {

    @Override
    public int execute(String sql) {
        return sql.length();
    }

    @Override
    public int execute(String sql, Object... args) {
        return sql.length() + args.length * 10000;
    }

}
