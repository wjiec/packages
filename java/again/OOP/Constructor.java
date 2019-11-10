package OOP;

public class Constructor {

    public static void main(String[] args) {
        Basket basket = new Basket();
        System.out.println(basket.apple);
        System.out.println(basket.apple.name);
        System.out.println(basket.apple.count);
    }

}

class Apple {
    String name;
    int count;

    Apple() {}

    Apple(String n, int c) {
        name = n;
        count = c;
    }
}

class Basket {
    Apple apple; // null

    Basket() {}
}