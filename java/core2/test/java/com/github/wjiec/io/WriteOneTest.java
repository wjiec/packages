package com.github.wjiec.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class WriteOneTest {

    public static void main(String[] args) {
        try (OutputStream stream = new ByteArrayOutputStream()) {
            stream.write(65);
            System.out.println(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
