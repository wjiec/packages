package com.github.wjiec.security;

import java.security.PrivilegedAction;

public class SysPropAction implements PrivilegedAction<String> {

    private String propName;

    public SysPropAction(String name) {
        propName = name;
    }

    @Override
    public String run() {
        return System.getProperty(propName);
    }

}
