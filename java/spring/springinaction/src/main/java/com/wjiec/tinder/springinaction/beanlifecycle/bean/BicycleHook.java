package com.wjiec.tinder.springinaction.beanlifecycle.bean;

public class BicycleHook extends Bicycle {

    public void initHook() {
        log("initHook");
    }

    public void destroyHook() {
        log("destroyHook");
    }

}
