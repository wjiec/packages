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
            StringBuilder name = new StringBuilder();
            for (char c = input.readChar(); c != 0; c = input.readChar()) {
                name.append(c);
            }

            int age = input.readInt();
            input.readChar(); // skip 0
            return new Person(name.toString(), age);
        } catch (IOException e) {
            if (e instanceof EOFException) {
                return null;
            }
            throw e;
        }
    }

}
