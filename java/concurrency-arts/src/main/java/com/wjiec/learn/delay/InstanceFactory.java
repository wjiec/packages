package com.wjiec.learn.delay;

public class InstanceFactory {

    private static class InstanceHolder {
        private static Instance instance = new Instance();
    }

    public static Instance getInstance() {
        return InstanceHolder.instance;
    }

}
