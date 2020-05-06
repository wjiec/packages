package com.github.wjiec.io;

import com.github.wjiec.human.Person;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

public class PersonRandomReader {

    private RandomAccessFile stream;

    private Map<Integer, Integer> coordinates;

    public PersonRandomReader(String filename) throws IOException {
        stream = new RandomAccessFile(filename, "r");
        coordinates = new HashMap<>();
        coordinates.put(0, 0);
    }

    public Person read(int offset) throws IOException {
        var index = coordinates.get(offset);
        if (index == null) {
            int last;
            for (last = offset - 1; last >= 0; --last) {
                if (coordinates.get(last) != null) {
                    break;
                }
            }

            for (; last <= offset; ++last) {
                index = stream.readInt() + 8; // length + age = 8
                coordinates.put(last + 1, index);
                stream.seek(index);
            }
        }
        stream.seek(index);

        int length = stream.readInt();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            builder.append(stream.readChar());
        }
        return new Person(builder.toString(), stream.readInt());
    }

}
