package com.github.wjiec.io;

import java.io.IOException;

public class MkdirTest {

    public static void main(String[] args) {
        boolean recursive = false;
        for (var s : args) {
            if (s.equals("-p") || s.equals("--recursive")) {
                recursive = true;
                break;
            }
        }

        try {
            Mkdir.execute(args[args.length - 1], recursive);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
