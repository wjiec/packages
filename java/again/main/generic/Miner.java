package main.generic;

public class Miner {

    public static <T extends Comparable<T>> T min(T[] s) {
        //                             ^^^ here
        if (s == null || s.length == 0) {
            return null;
        }

        T r = s[0];
        for (T t : s) {
            // r - t > 0
            if (r.compareTo(t) > 0) {
                r = t;
            }
        }
        return r;
    }

}
