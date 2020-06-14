package com.github.wjiec.security;

public class SecurityManager extends java.lang.SecurityManager {

    @Override
    public void checkExit(int status) {
        throw new RuntimeException("exit not allowed");
    }

}
