package com.github.wjiec.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Mkdir {

    public static void execute(String dir, boolean recursive) throws IOException {
        Path target = Paths.get(dir);
        if (recursive) {
            Files.createDirectories(target);
        } else {
            Files.createDirectory(target);
        }
    }

}
