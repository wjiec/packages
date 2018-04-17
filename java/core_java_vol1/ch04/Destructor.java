public class Destructor {
    public Destructor() {}

    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalize has called");
    }

    public static void main(String[] args) {
        do {
            Object destructor = new Destructor();
            System.out.println(destructor);

            System.gc();

            destructor = new Object();
            System.out.println(destructor);
        } while (false);
    }
}
