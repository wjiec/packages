package com.github.wjiec.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class MessageDigestTest {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        MessageDigest digest1 = MessageDigest.getInstance("SHA-1");
        digest1.update("Hello".getBytes());
        System.out.println(Arrays.toString(digest1.digest()));

        MessageDigest digest2 = MessageDigest.getInstance("SHA-1");
        digest2.update("hello".getBytes());
        System.out.println(Arrays.toString(digest2.digest()));
    }

}
