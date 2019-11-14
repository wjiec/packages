package abstractstest.human;

import abstracts.human.Employee;
import abstracts.human.Person;
import abstracts.human.Student;

public class PersonTest {

    public static void main(String[] args) {
        Person[] people = new Person[2];
        people[0] = new Student("Jack", "Compute Science");
        people[1] = new Employee("Lisa", 2000);

        for (Person person : people) {
            System.out.println(person.getDescription());
        }
    }

}
