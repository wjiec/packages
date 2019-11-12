package tests;

import inherit.company.staff.Employee;
import inherit.company.staff.Manager;

public class ManagerTest {

    public static void main(String[] args) {
        Manager boss = new Manager("Cook", 3000);
        boss.setBonus(4000);
        System.out.println(boss.getSalary());

        Employee bossCopy = boss;
        System.out.println(bossCopy.getSalary());

        bossCopy.raiseSalary(10);
        System.out.println(boss.getSalary());
        System.out.println(bossCopy.getSalary());
    }

}
