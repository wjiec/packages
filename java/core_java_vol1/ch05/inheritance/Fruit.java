package inheritance;

import java.util.Date;
import java.util.Objects;

public abstract class Fruit {
    private double unitPrice;

    private int count;

    private Date createDate;

    public Fruit(double unitPrice, int count) {
        this.unitPrice = unitPrice;
        this.count = count;
        this.createDate = new Date();
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

    public Date getCreateDate() {
        return createDate;
    }

    public double getTotalPrice() {
        return getCount() * getUnitPrice();
    }

    public abstract Fruit replace();

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }

        Fruit fruit = (Fruit) obj;
        return unitPrice == fruit.unitPrice
                && count == fruit.count
                && Objects.equals(createDate, fruit.createDate);
    }
}
