package main.generic;

import java.io.Serializable;

public class MinSerializer {

    public static <T extends Comparable<T> & Serializable> String min(T[] s) {
        T r = Miner.min(s);
        if (r != null) {
            return r.toString();
        }
        return null;
    }

}
