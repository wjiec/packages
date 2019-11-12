package inherit.company.staff;

public class Manager extends Employee {

    private double bonus = 0;

    public Manager(String name, double salary) {
        super(name, salary);
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    @Override
    public double getSalary() {
        return super.getSalary() + bonus;
    }
}
