package tests.asserts;

import main.asserts.AssertLoader;

import java.lang.reflect.Constructor;
import java.util.Random;

public class AssertLoaderTest {

    public static void main(String[] args) {
        // failure
        ClassLoader loader = new AssertLoader();

        try {
            Class cl = loader.loadClass("main.asserts.AssertClass");

            Constructor constructor = cl.getConstructor(int.class);
            constructor.newInstance(2 - new Random().nextInt(5));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
