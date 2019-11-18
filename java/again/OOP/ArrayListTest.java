package OOP;

import abstracts.human.Employee;
import abstracts.human.Person;
import abstracts.human.Student;

import java.util.ArrayList;

public class ArrayListTest {

    public static void main(String[] args) {
        ArrayList<Person> people = new ArrayList<>();
        for (int i = 0; i < 1000; ++i) {
            if (i % 2 == 0) {
                people.add(new Student("student" + i, "math"));
            } else {
                people.add(0, new Employee("employee" + i, i * 1000));
            }
        }

        for (Person person : people) {
            System.out.println(person);
        }
    }

}
