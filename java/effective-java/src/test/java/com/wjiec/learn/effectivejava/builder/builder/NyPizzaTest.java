package com.wjiec.learn.effectivejava.builder.builder;

public class NyPizzaTest {

    public static void main(String[] args) {
        Pizza smallPizza = new NyPizza.Builder(NyPizza.Size.SMALL)
            .addTopping(Pizza.Topping.HAM)
            .addTopping(Pizza.Topping.MUSHROOM)
            .build();
        System.out.println(smallPizza);

        Pizza mediumPizza = new NyPizza.Builder(NyPizza.Size.MEDIUM)
            .addTopping(Pizza.Topping.SAUSAGE)
            .addTopping(Pizza.Topping.MUSHROOM)
            .build();
        System.out.println(mediumPizza);

        Pizza largePizza = new NyPizza.Builder(NyPizza.Size.LARGE)
            .addTopping(Pizza.Topping.ONION).build();
        System.out.println(largePizza);
    }

}
