package tests.reflection;

import inherit.company.staff.Employee;
import inherit.company.staff.Manager;
import main.reflection.ArrayCopier;

import java.util.Arrays;

public class ArrayCopierTest {

    public static void main(String[] args) {
        Employee[] employees = new Employee[3];
        employees[0] = new Employee("e1", 1);
        employees[1] = new Employee("e2", 1);
        employees[2] = new Manager("e3", 1);
        System.out.println(Arrays.toString(employees));

        employees = (Employee[])ArrayCopier.copyOf(employees, 5);
        System.out.println(Arrays.toString(employees));

        employees = (Employee[])ArrayCopier.copyOf(employees, 2);
        System.out.println(Arrays.toString(employees));
    }

}
