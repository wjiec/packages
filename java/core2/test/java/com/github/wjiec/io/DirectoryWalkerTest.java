package com.github.wjiec.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class DirectoryWalkerTest {

    public static void main(String[] args) {
        DirectoryWalker walker = new DirectoryWalker(System.getProperty("user.dir"));
        try (var s = walker.walk()) {
            for (var p : s.collect(Collectors.toList())) {
                System.out.println(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println();
        try (var s = Files.walk(Paths.get(System.getProperty("user.dir")))) {
            for (var p : s.collect(Collectors.toList())) {
                System.out.println(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
