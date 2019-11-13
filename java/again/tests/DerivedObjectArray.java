package tests;

import inherit.company.staff.Employee;
import inherit.company.staff.Manager;

public class DerivedObjectArray {

    public static void main(String[] args) {
        Manager[] boss = new Manager[10];
        for (int i = 0; i < boss.length; i++) {
            boss[i] = new Manager("boss" + i, i * 1000 + 1000);
            boss[i].setBonus(i * 1000);
        }

        Employee[] staff = boss;
        for (Employee employee : staff) {
            System.out.println(employee.getName() + " " + employee.getSalary());
        }

        // ArrayStoreException
        // staff[0] = new Employee();
    }

}
