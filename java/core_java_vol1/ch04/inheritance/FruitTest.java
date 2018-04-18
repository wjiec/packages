package inheritance;

public class FruitTest {
    public static void main(String[] args) throws CloneNotSupportedException {
        Fruit[] fruits = new Fruit[4];

        for (int i = 0; i < fruits.length; ++i) {
            if (i % 2 == 0) {
                fruits[i] = new Apple(i + 0.99, i + 1, 0.99);
            } else {
                fruits[i] = new Banana(i + 0.99, i + 1, Math.random() % 1);
            }
        }

        Apple lastApple = null;
        for (Fruit fruit : fruits) {
            System.out.printf("%s: %d * %f = %f\n", fruit.getClass().getName(), fruit.getCount(), fruit.getUnitPrice(), fruit.getTotalPrice());

            Fruit newFruit = fruit.replace();
            System.out.printf("%s: %d * %f = %f\n", newFruit.getClass().getName(), newFruit.getCount(), newFruit.getUnitPrice(), newFruit.getTotalPrice());

            if (fruit instanceof Apple) {
                lastApple = (Apple) fruit;
            }

            if (fruit instanceof Banana && lastApple != null) {
                ((Banana) fruit).testProtectedCase(lastApple);
            }
        }
    }
}
