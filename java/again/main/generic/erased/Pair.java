package main.generic.erased;

public class Pair<T> {

    protected T first;

    protected T second;

    public Pair(T first, T second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public T getSecond() {
        return second;
    }

    public void setSecond(T second) {
        System.out.println("Pair::setSecond");
        this.second = second;
    }
}
