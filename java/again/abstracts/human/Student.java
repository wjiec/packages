package abstracts.human;

import inherit.company.staff.Manager;

import java.util.Objects;

public class Student extends Person {

    private String major;

    public Student(String name, String major) {
        super(name);

        this.major = major;
    }

    public String getMajor() {
        return major;
    }

    @Override
    public String getDescription() {
        return String.format("I'm %s, majoring in %s", getName(), getMajor());
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }

        Student student = (Student)obj;
        return Objects.equals(major, student.major);
    }

}
