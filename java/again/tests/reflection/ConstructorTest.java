package tests.reflection;

import inherit.company.staff.Employee;

import java.lang.reflect.Constructor;

public class ConstructorTest {

    public static void main(String[] args) {
        try {
            Constructor constructor = Employee.class.getConstructor(String.class, double.class);
            Employee employee = (Employee)constructor.newInstance("Herny", 3000);
            System.out.println(employee.getName() + " " + employee.getSalary());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
