package com.github.wjiec.io;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class ZipFsTest {

    public static void main(String[] args) throws IOException {
        FileSystem fs = FileSystems.newFileSystem(Paths.get("src.zip"), null);
        Files.walkFileTree(fs.getPath("/"), new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                System.out.printf("%s: \n%s", file, attrs);
                return FileVisitResult.CONTINUE;
            }
        });
    }

}
