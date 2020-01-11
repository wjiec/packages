package main.generic.helper;

import main.generic.erased.Pair;

import java.nio.file.attribute.AclFileAttributeView;

public class Maker {

    public static <T> Pair<T> make(Class<T> cl) throws IllegalAccessException, InstantiationException {
        return new Pair<>(cl.newInstance(), cl.newInstance());
    }

}
