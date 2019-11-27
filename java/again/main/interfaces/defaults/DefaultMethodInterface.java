package main.interfaces.defaults;

public interface DefaultMethodInterface {

    int size();

    default boolean isEmpty() {
        return size() == 0;
    }

}
