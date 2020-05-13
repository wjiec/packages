package com.github.wjiec.io;

import com.github.wjiec.math.Point;

import java.io.*;

public class TransientTest {

    public static void main(String[] args) {
        try (OutputStream file = new FileOutputStream("transient.dat")) {
            ObjectOutputStream serialization = new ObjectOutputStream(file);
            serialization.writeObject(new Point("p0", 1, 2));
            serialization.writeObject(new Point("p1", 3, 4));

            serialization.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (InputStream file = new FileInputStream("transient.dat")) {
            ObjectInputStream serialization = new ObjectInputStream(file);
            Point p0 = (Point)serialization.readObject();
            Point p1 = (Point)serialization.readObject();

            System.out.println(p0);
            System.out.println(p1);

            serialization.close();
        } catch (IOException|ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
