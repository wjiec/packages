package main.interfaces;

import main.reflection.Analyzer;

public class Level implements Comparable<Level>, Cloneable {

    private int level;

    public Level(int level) {
        this.level = level;
    }

    @Override
    public int compareTo(Level other) {
        return Integer.compare(level, other.level);
    }

    @Override
    public String toString() {
        return new Analyzer().analysis(this);
    }

}
