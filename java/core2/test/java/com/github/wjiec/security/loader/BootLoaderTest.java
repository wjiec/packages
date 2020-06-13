package com.github.wjiec.security.loader;

import com.github.wjiec.script.HumanInterface;

import java.sql.DriverManager;

public class BootLoaderTest {

    public static void main(String[] args) {
        System.out.println(StringBuffer.class.getClassLoader());
        System.out.println(DriverManager.class.getClassLoader());
        System.out.println(HumanInterface.class.getClassLoader());

        System.out.println(Thread.currentThread().getContextClassLoader());
    }

}
