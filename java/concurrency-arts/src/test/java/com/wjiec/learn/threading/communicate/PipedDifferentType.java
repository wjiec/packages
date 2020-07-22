package com.wjiec.learn.threading.communicate;

import java.io.PipedInputStream;
import java.io.PipedWriter;

public class PipedDifferentType {

    public static void main(String[] args) {
        PipedInputStream in = new PipedInputStream();
        PipedWriter out = new PipedWriter();
//        in.connect(out);
    }

}
