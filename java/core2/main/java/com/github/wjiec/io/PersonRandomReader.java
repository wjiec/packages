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
        coordinates = new HashMap<>() {{ put(0, 0); }};
    }

    public Person read(int offset) throws IOException {
        Integer index = coordinates.get(offset);
        if (index == null) {
            int last = offset - 1;
            index = coordinates.get(last);
            while (index == null) {
                --last;
                index = coordinates.get(last);
            }

            stream.seek(index);
            do {
                index += stream.readInt() * 2 + 8; // skip length + age
                if (index >= stream.length()) {
                    return null;
                }
                coordinates.put(++last, index);
                stream.seek(index);
            } while (last < offset);
        }

        int length = stream.readInt();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            builder.append(stream.readChar());
        }
        return new Person(builder.toString(), stream.readInt());
    }

}
