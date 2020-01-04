package main.generic.erased;

import java.time.LocalDate;

public class Interval extends Pair<LocalDate> {

    public Interval(LocalDate first, LocalDate second) {
        super(first, second);
    }

    @Override
    public void setSecond(LocalDate second) {
        System.out.println("Interval::setSecond");
        this.second = second;
    }
}
