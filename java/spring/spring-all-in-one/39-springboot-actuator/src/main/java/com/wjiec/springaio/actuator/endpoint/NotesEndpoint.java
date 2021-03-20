package com.wjiec.springaio.actuator.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Endpoint(id = "notes", enableByDefault = true)
public class NotesEndpoint {

    private List<String> notes = new ArrayList<>();

    @ReadOperation
    public List<String> notes() {
        return notes;
    }

    @WriteOperation
    public List<String> writeNote(String text) {
        notes.add(text);
        return notes;
    }

    @DeleteOperation
    public List<String> deleteNote() {
        notes.removeIf(s -> true);
        return notes;
    }

}
