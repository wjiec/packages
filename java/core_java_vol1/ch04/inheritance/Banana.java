package inheritance;

public class Banana extends Fruit{
    private double discount;

    public Banana(double unitPrice, int count, double discount) {
        super(unitPrice, count);

        this.discount = discount;
    }

    @Override
    public double getTotalPrice() {
        return super.getTotalPrice() * discount;
    }

    @Override
    public Banana replace() {
        return new Banana(getUnitPrice(), getCount(), discount);
    }

    protected void testProtectedCase(Apple apple) {
        System.out.println(apple.protectedCase());
    }
}
