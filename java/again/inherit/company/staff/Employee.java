package inherit.company.staff;

import java.time.LocalDate;


public class Employee {

    private final String name;

    private double salary = 0;

    private final LocalDate hireDay;

    public Employee() {
        this.name = "";
        this.hireDay = LocalDate.now();
    }

    public Employee(String name, double salary) {
        this(name, salary, LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
    }

    public Employee(String name, double salary, int year, int month, int day) {
        this.name = name;
        this.salary = salary;
        this.hireDay = LocalDate.of(year, month, day);
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public LocalDate getHireDay() {
        return hireDay;
    }

    public void raiseSalary(double rate) {
        salary *= 1 + rate / 100;
    }

}
