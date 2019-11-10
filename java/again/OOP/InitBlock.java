package OOP;

public class InitBlock {

    public static void main(String[] args) {
        Block block = new Block();
        System.out.println(block.x);
        System.out.println(block.y);
        System.out.println(block.z);
    }

}

class Block {

    int x = AutoIncrement.next();

    int z;

    int y;

    {
        y = AutoIncrement.next();
    }

    Block() {
        z = AutoIncrement.next();
    }

}
