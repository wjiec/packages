package interfaces;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

public class DeepCopy {
    public static void main(String[] args) throws CloneNotSupportedException {
        Apple apple = new Apple();
        apple.setBirth(2000, 1, 1);

        Apple copyApple = apple.clone();
        copyApple.setBirth(2008, 8, 8);

        System.out.println(apple);
        System.out.println(copyApple);
    }
}


class Apple implements Cloneable {
    private Date birth;

    Apple() {
        this.birth = new Date();
    }

    void setBirth(int year, int month, int day) {
        Date newDate = new GregorianCalendar(year, month, day).getTime();
        this.birth.setTime(newDate.getTime());
    }

    @Override
    public String toString() {
        return String.format("%s[birth=%s]\n", getClass().getName(), birth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(birth);
    }

    @Override
    protected Apple clone() throws CloneNotSupportedException {
        Apple cloned = (Apple) super.clone();

        cloned.birth = (Date) birth.clone();
        return cloned;
    }
}