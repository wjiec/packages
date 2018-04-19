package inheritance;

public class Apple extends Fruit {
    private double raise;

    public Apple(double unitPrice, int count, double raise) {
        super(unitPrice, count);

        this.raise = raise;
    }

    protected String protectedCase() {
        return "Apple protected method";
    }

    @Override
    public double getTotalPrice() {
        return super.getTotalPrice() + getCount() * raise;
    }

    @Override
    public Fruit replace() {
        return new Apple(this.getUnitPrice(), getCount(), raise);
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }

        Apple apple = (Apple) obj;
        return raise == apple.raise;
    }
}
