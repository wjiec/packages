package com.wjiec.learn.effectivejava.singleton;

import java.io.Serializable;

public class Lover implements Serializable {

    public static final Lover INSTANCE = new Lover();
    private final Ring ring;

    private Lover() {
        ring = new Ring();
        System.out.println("Lover.Lover ringId=" + ring.getId());
    }

    public int getRingId() {
        return ring.getId();
    }

    public static Lover getInstance() {
        return LoverHolder.INSTANCE;
    }

    private static class LoverHolder {
        private static final Lover INSTANCE = new Lover();
    }

    public static enum SINGLETON {
    }

}
