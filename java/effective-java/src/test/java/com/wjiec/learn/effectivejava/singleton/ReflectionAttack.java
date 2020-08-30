package com.wjiec.learn.effectivejava.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ReflectionAttack {

    public static void main(String[] args) {
        System.out.println(Lover.INSTANCE);
        System.out.println(Lover.getInstance());

        try {
            Constructor<?> constructor = Lover.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            System.out.println(constructor.newInstance());
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }

        System.out.println(PerfectLover.INSTANCE);
        System.out.println(PerfectLover.getInstance());

        try {
            Constructor<?> constructor = PerfectLover.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            System.out.println(constructor.newInstance());
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
