package com.github.wjiec.xml;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;

public class Books {

    public static File asFile() throws URISyntaxException {
        return new File(Books.class.getResource("/books.xml").toURI());
    }

    public static InputStream asStream() {
        return Books.class.getResourceAsStream("/books.xml");
    }

}
