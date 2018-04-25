package reflection;

import com.sun.org.apache.xpath.internal.operations.Mod;

import java.lang.reflect.*;
import java.util.Scanner;

public class ReflectionTest {
    public static void main(String[] args) {
        while (true) {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Please input a class: ");
            String className = scanner.next();
            Class classObject = null;

            try {
                classObject = Class.forName(className);
            } catch (Exception e) {
                System.out.printf("Class not found: %s\n", className);
                break;
            }

            String modifier = Modifier.toString(classObject.getModifiers());
            Class superClassObject = classObject.getSuperclass();

            System.out.printf("%s class %s %s %s {\n",
                modifier,
                classObject.getName(),
                (superClassObject == null || superClassObject == Object.class) ? "\b" : "extends",
                (superClassObject == null || superClassObject == Object.class) ? "\b" : superClassObject.getName()
            );

            printConstructors(classObject.getDeclaredConstructors());
            System.out.println();

            printMethods(classObject.getDeclaredMethods());
            System.out.println();

            printFields(classObject.getDeclaredFields());

            System.out.println("}");
        }
    }

    private static void printConstructors(Constructor[] constructors) {
        for (Constructor constructor : constructors) {
            printMethodSignature(constructor);
        }
    }

    private static void printMethods(Method[] methods) {
        for (Method method : methods) {
            printMethodSignature(method);
        }
    }

    private static void printMethodSignature(Executable executable) {
        String modifier = Modifier.toString(executable.getModifiers());
        Parameter[] parameters = executable.getParameters();
        String stringPrams = "";
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            stringPrams += String.format(" %s %s%s ",
                    parameter.getType().getName(), parameter.getName(),
                    i == parameters.length - 1 ? "" : ","
            );
        }

        System.out.printf("%s%s %s(%s);\n", "\t", modifier, executable.getName(), stringPrams);
    }

    private static void printFields(Field[] fields) {
        for (Field field : fields) {
            String modifier = Modifier.toString(field.getModifiers());

            System.out.printf("%s%s %s %s;\n", "\t", modifier, field.getType().getName(), field.getName());
        }
    }
}
