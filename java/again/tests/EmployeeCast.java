package tests;

import inherit.company.staff.Employee;
import inherit.company.staff.Manager;

public class EmployeeCast {

    public static void main(String[] args) {
        Employee[] staff = new Employee[3];
        staff[0] = new Manager("Boss", 1000);
        staff[1] = new Employee("Employee1", 200);
        staff[2] = new Employee("Employee2", 250);

        Manager boss = (Manager)staff[0];
        boss.setBonus(2000);
        System.out.println(staff[0].getSalary());

//        Manager fake = (Manager)staff[1]; // ClassCastException
//        fake.setBonus(2000);
//        System.out.println(staff[1].getSalary());
    }

}
