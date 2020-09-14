package com.wjiec.tinder.springinaction.moreaop.encore;

public class DefaultEncoreableImpl implements Encoreable {

    @Override
    public void performEncore(String name, int count) {
        System.out.println("performance encore: " + name + " x " +count);
    }

}
