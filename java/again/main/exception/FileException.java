package main.exception;

import java.io.IOException;

public class FileException extends IOException {

    public FileException() {}

    public FileException(String s) {
        super(s);
    }

}
