package tests.reflection;

import abstracts.human.Person;
import main.reflection.Dumper;

public class DumperTest {

    public static void main(String[] args) {
        Dumper dumper = new Dumper(Person.class);
        System.out.println(dumper.toString());
    }

}
