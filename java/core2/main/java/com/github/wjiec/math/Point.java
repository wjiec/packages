package com.github.wjiec.math;

import java.io.*;

public class Point implements Serializable {

    public String name;

    public transient double x;

    public transient double y;

    public Point(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        System.out.println("readObject");
        stream.defaultReadObject();
        this.setX(stream.readDouble());
        this.setY(stream.readDouble());
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        System.out.println("writeObject");
        stream.defaultWriteObject();
        stream.writeDouble(getX());
        stream.writeDouble(getY());
    }

    @Override
    public String toString() {
        return String.format("<%s x=%f, y=%f>", name, x, y);
    }
}
