package com.github.wjiec.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class DirectoryWalker {

    private Path root;

    public DirectoryWalker(Path path) {
        root = path;
    }

    public DirectoryWalker(String path) {
        this(Paths.get(path));
    }

    public Stream<Path> walk() throws IOException {
        return Files.list(root).flatMap((p) -> {
            if (Files.isDirectory(p)) {
                try {
                    return Stream.concat(Stream.of(p), new DirectoryWalker(p).walk());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return Stream.of(p);
        });
    }

}
