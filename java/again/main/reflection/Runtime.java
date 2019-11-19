package main.reflection;

import inherit.company.staff.Employee;

import java.util.Arrays;

public class Runtime {

    public static void main(String[] args) {
        Employee employee = new Employee("Stave", 3000);
        Class cl0 = employee.getClass();
        System.out.println(cl0.getName() + " " + employee.getName());

        try {
            Class cl1 = Class.forName("java.util.Random1111");
            System.out.println(cl1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // void.class
        System.out.println("void.class: ");
        Class cl2 = void.class;
        System.out.println(cl2.getName());
        System.out.println(cl2.getSuperclass());
        System.out.println(Arrays.toString(cl2.getSigners()));
        System.out.println();

        // PRIME.class
        System.out.println("int.class: ");
        Class cl3 = int.class;
        System.out.println(cl3.getName());
        System.out.println(cl3.getSuperclass());
        System.out.println(Arrays.toString(cl3.getSigners()));
        System.out.println();

        // PRIME[].class
        System.out.println("int[].class: ");
        Class cl4 = int[].class;
        System.out.println(cl4.getName());
        System.out.println(cl4.getSuperclass());
        System.out.println(Arrays.toString(cl4.getSigners()));
        System.out.println();
    }

}
