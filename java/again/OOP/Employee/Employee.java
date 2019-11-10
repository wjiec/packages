package OOP.Employee;

import java.time.LocalDate;

public class Employee {

    /**
     * The name of the employee
     */
    private final String name;

    /**
     * The salary of the employee
     */
    private double salary;

    /**
     * Time for employee hired
     */
    private final LocalDate hireDay;

    /**
     * @param name The name of the employee
     * @param salary The salary of the employee
     */
    public Employee(String name, double salary) {
        this(name, salary, LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
    }

    /**
     * @param name The name of the employee
     * @param salary The salary of the employee
     * @param year Year of the  employee hired
     * @param month Month of the  employee hired
     * @param day Date of the  employee hired
     */
    public Employee(String name, double salary, int year, int month, int day) {
        this.name = name;
        this.salary = salary;
        this.hireDay = LocalDate.of(year, month, day);
    }

    /**
     * @return The name of the employee
     */
    public String getName() {
        return name;
    }

    /**
     * @return The salary of the employee
     */
    public double getSalary() {
        return salary;
    }

    /**
     * @return Time for employee hired
     */
    public LocalDate getHireDay() {
        return hireDay;
    }

    public void raiseSalary(double rate) {
        salary *= 1 + rate / 100;
    }

}
