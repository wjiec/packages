package com.github.wjiec.security.loader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class SecurityClassLoaderTest {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("LoaderKey: ");
            ClassLoader loader = new SecurityClassLoader(scanner.nextInt());
            scanner.nextLine();

            System.out.print("BootClass: ");
            Class<?> cl = loader.loadClass(scanner.nextLine());
            Method main = cl.getMethod("main", String[].class);
            if (main == null) {
                System.err.println("Unknown main method");
                System.exit(1);
            }

            main.invoke(null, (Object) new String[] {});
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
