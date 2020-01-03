package main.generic;

public class Pair<T> {

    private T first;

    private T second;

    {
        first = null;
        second = null;
    }

    public Pair() {}

    public Pair(T f, T s) {
        first = f;
        second = s;
    }

    public static <S> Pair<S> factory(S f, S s) {
        return new Pair<>(f, s);
    }

    public T getFirst() {
        return first;
    }

    public T getSecond() {
        return second;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public void setSecond(T second) {
        this.second = second;
    }

}
