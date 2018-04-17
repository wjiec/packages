import java.util.Random;

public class StaticInitializationBlock {
    private int id;

    private String name;

    private static int nextId;

    static {
        Random generator = new Random();
        nextId = generator.nextInt();

        System.out.println("initialization nextId = " + nextId);
    }

    {
        id = nextId++;
    }

    public StaticInitializationBlock(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + id;
    }

    public static void main(String[] args) {
        System.out.println(new StaticInitializationBlock("block-"));
        System.out.println(new StaticInitializationBlock("block-"));
        System.out.println(new StaticInitializationBlock("block-"));
        System.out.println(new StaticInitializationBlock("block-"));
        System.out.println(new StaticInitializationBlock("block-"));
    }
}
