package OOP;

public class Explicit {

    public static void main(String[] args) {
        for (int i = 0; i < 3; ++i) {
            Sequence sequence = new Sequence();
            System.out.println(sequence.n1);
            System.out.println(sequence.n2);
            System.out.println(sequence.n3);
        }
    }

}

class AutoIncrement {

    private static int id = 0;

    static int next() {
        return id++;
    }

}

class Sequence {

    int n1 = AutoIncrement.next();

    int n2 = AutoIncrement.next();

    int n3 = 0;

    Sequence() {
        n3 = AutoIncrement.next();
    }

}