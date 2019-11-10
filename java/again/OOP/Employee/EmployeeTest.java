package OOP.Employee;

public class EmployeeTest {

    public static void main(String[] args) {
        Employee[] employees = new Employee[3];

        employees[0] = new Employee("John", 1000, 2019, 1, 1);
        employees[1] = new Employee("Lisa", 800, 2019, 6, 1);
        employees[2] = new Employee("Dennis", 500);

        for (int i = 0; i < 5; ++i) {
            for (Employee employee : employees) {
                employee.raiseSalary(5);
            }

            System.out.printf("%d Year(s):\n", i + 1);
            for (Employee e : employees) {
                System.out.printf("Employee name=%s, salary=%f, hiredDay=%s\n",
                    e.getName(), e.getSalary(), e.getHireDay());
            }
        }
    }

}
