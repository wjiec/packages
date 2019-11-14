package OOP;

public class ProtectedTest {

    public static void main(String[] args) {
        Student student = new Student();
        Doctor doctor = new Doctor();

        student.name = "Alice";
        doctor.name = "Bob";

        student.view(doctor);
        doctor.view(student);
    }

}

class Person {
    protected String name;
}

class Student extends Person {
    void view(Person p) {
        System.out.println("Student: view -> " + p.name);
    }
}

class Doctor extends Person {
    void view(Person p) {
        System.out.println("Doctor: view -> " + p.name);
    }
}