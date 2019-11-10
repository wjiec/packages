package OOP;

import java.util.Random;

public class StaticBlock {

    public static void main(String[] args) {
        System.out.println(RandomAutoIncrement.next());
        System.out.println(RandomAutoIncrement.next());
        System.out.println(RandomAutoIncrement.next());
    }

}

class RandomAutoIncrement {

    static int id;

    static {
        Random generator = new Random();
        id = generator.nextInt(10000);
    }

    static int next() {
        return id++;
    }

}
