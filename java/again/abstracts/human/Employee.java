package abstracts.human;

public class Employee extends Person {

    private double salary;

    public Employee(String name, double salary) {
        super(name);

        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public String getDescription() {
        return String.format("I'm %s, the salary is %f", getName(), getSalary());
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }

        Employee employee = (Employee)obj;
        return salary == employee.salary;
    }
}
