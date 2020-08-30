package com.wjiec.learn.effectivejava.singleton;

import java.io.Serializable;

public class PerfectLover implements Serializable {

    private static final long serialVersionUID = 8294180014912103005L;
    public static final PerfectLover INSTANCE = Holder.INSTANCE;
    // to prevent serialize attack
    private final transient Ring ring;

    private PerfectLover() {
        // to prevent reflection attack
        if (INSTANCE != null) {
            throw new UnsupportedOperationException();
        }

        ring = new Ring();
        System.out.println("PerfectLover.PerfectLover ringId=" + ring.getId());
    }

    public int getRingId() {
        if (ring == null) {
            return -1;
        }
        return ring.getId();
    }

    // to prevent serialize attack
    private Object readResolve() {
        return INSTANCE;
    }

    public static PerfectLover getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final PerfectLover INSTANCE = new PerfectLover();
    }

}
