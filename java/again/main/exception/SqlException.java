package main.exception;

import java.io.IOException;

public class SqlException extends IOException {

    public SqlException() {}

    public SqlException(String s) {
        super(s);
    }

}
