package com.github.wjiec.security;

public class SecurityManagerTest {

    public static void main(String[] args) {
        System.setSecurityManager(new SecurityManager());

        System.out.println(123);
        System.exit(1);
    }

}
