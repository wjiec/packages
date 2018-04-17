public class InitializationBlock {
    private int id;

    private String name;

    private static int nextId = 0;

    {
        id = nextId++;
    }

    public InitializationBlock(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + id;
    }

    public static void main(String[] args) {
        System.out.println(new InitializationBlock("block-"));
        System.out.println(new InitializationBlock("block-"));
        System.out.println(new InitializationBlock("block-"));
        System.out.println(new InitializationBlock("block-"));
        System.out.println(new InitializationBlock("block-"));
    }
}
