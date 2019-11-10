package OOP;

public class Finalize {

    public static void main(String[] args) {
        while (true) {
            AutoIncrement.next();
            Resource resource = new Resource();
        }
    }

}

class Resource {

    @Override
    protected void finalize() throws Throwable {
        System.out.println(AutoIncrement.next());
        System.exit(0);
    }
}