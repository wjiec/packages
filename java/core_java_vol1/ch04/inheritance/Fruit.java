package inheritance;

public abstract class Fruit {
    private double unitPrice;

    private int count;

    public Fruit(double unitPrice, int count) {
        this.unitPrice = unitPrice;
        this.count = count;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getCount() {
        return count;
    }

    public double getTotalPrice() {
        return getCount() * getUnitPrice();
    }

    public abstract Fruit replace();
}
