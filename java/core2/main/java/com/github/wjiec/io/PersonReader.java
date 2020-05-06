package com.github.wjiec.io;

import com.github.wjiec.human.Person;

import java.io.*;

public class PersonReader {

    private DataInputStream input;

    public PersonReader(InputStream input) {
        this.input = new DataInputStream(new PushbackInputStream(input));
    }

    public Person read() throws IOException {
        try {
            int length = input.readInt();
            StringBuilder name = new StringBuilder();
            for (var i = 0; i < length; ++i) {
                name.append(input.readChar());
            }

            int age = input.readInt();
            return new Person(name.toString(), age);
        } catch (IOException e) {
            if (e instanceof EOFException) {
                return null;
            }
            throw e;
        }
    }

}
