package com.github.wjiec.io;

import com.github.wjiec.human.Person;

import java.io.DataOutputStream;
import java.io.Flushable;
import java.io.IOException;
import java.io.OutputStream;

public class PersonWriter implements Flushable {

    private DataOutputStream output;

    public PersonWriter(OutputStream output) {
        this.output = new DataOutputStream(output);
    }

    public void write(Person person) throws IOException {
        output.writeChars(person.getName());
        output.writeChar(0);

        output.writeInt(person.getAge());
        output.writeChar(0);
    }

    @Override
    public void flush() throws IOException {
        output.flush();
    }
}
