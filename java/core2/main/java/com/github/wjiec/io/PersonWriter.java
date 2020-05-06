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
        output.writeInt(person.getName().length());
        output.writeChars(person.getName());
        output.writeInt(person.getAge());
    }

    @Override
    public void flush() throws IOException {
        output.flush();
    }
}
