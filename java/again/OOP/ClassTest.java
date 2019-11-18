package OOP;

import abstracts.human.Student;

public class ClassTest {

    public static void main(String[] args) {
        Student student = new Student("Jack", "Program");
        for (Class c = student.getClass(); c != null; ) {
            System.out.println(c.getName());
            c = c.getSuperclass();
        }
    }

}
