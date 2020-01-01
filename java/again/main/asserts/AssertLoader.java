package main.asserts;

public class AssertLoader extends ClassLoader {

    public AssertLoader() {
        setDefaultAssertionStatus(false);
    }

}
