package com.github.wjiec.io;

import com.github.wjiec.stream.AliceInWonderLand;

import java.io.IOException;
import java.io.InputStream;

public class ReadOneTest {

    public static void main(String[] args) {
        try (InputStream txt = AliceInWonderLand.class.getResourceAsStream("/aliceinwonderland.txt")) {
            int b = txt.read();
            System.out.println(b);
            System.out.println((char)b);
            System.out.println(String.valueOf((char)b));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
