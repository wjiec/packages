package com.github.wjiec.io;

import com.github.wjiec.human.Person;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class PersonRandomReader {

    private RandomAccessFile stream;

    private List<Integer> coordinates;

    public PersonRandomReader(String filename) throws IOException {
        stream = new RandomAccessFile(filename, "r");
        coordinates = new ArrayList<>((int)(stream.length() / RECORD_AVG_SIZE));
    }

    public Person read(int offset) throws IOException {
        var index = coordinates.get(offset);
        if (index == 0 && offset != 0) {

        }

        return null;
    }

    private final int RECORD_AVG_SIZE = 16;

}
