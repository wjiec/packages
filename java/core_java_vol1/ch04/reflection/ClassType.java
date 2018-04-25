package reflection;

import java.util.Scanner;

public class ClassType {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        System.out.println(Apple.class);
        System.out.println(Apple.class == (new Apple()).getClass());

        Class cl = Apple.class;
        String clName = cl.getName();
        Class cl1 = Class.forName(clName);
        System.out.println(cl == cl1);

        Object apple = cl1.newInstance();
        System.out.println(((Apple) apple).name);
        System.out.println(((Apple) apple).count);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Please input a class name: ");
        String className = scanner.next();

        Object object = Class.forName(className);
        System.out.println(object);
    }
}


class Apple {
    String name;

    int count;
}
