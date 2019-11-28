package tests.oop.clone;

import main.oop.clone.Data;

public class DataTest {

    public static void main(String[] args) throws CloneNotSupportedException {
        Data data0 = new Data();
        data0.append(1);
        data0.append(2);
        data0.append(3);
        System.out.println(data0);

        Data data1 = data0;
        data1.append(4);
        System.out.println(data0);
        System.out.println(data1);

        Data data2 = data0.clone();
        data2.append(5);
        System.out.println(data0);
        System.out.println(data1);
        System.out.println(data2);
    }

}
