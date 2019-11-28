package main.oop.clone;

import java.util.Arrays;

public class Data implements Cloneable {

    private int index;

    private int[] numbers;

    {
        index = 0;
        numbers = new int[10];
    }

    public void append(int n) {
        numbers[index++] = n;
    }

    @Override
    public Data clone() throws CloneNotSupportedException {
        Data data =  (Data)super.clone();
        data.numbers = numbers.clone();

        return data;
    }

    @Override
    public String toString() {
        return Arrays.toString(numbers);
    }
}
