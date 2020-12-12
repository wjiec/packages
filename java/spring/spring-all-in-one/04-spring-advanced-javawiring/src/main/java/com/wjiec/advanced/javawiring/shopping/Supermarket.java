package com.wjiec.advanced.javawiring.shopping;

import com.wjiec.advanced.javawiring.annotation.Cold;
import com.wjiec.advanced.javawiring.annotation.Hot;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

@Configuration
public class Supermarket {

    @Bean
    @Profile("father")
    public Game game() {
        return new Game();
    }

    public static class Game implements Item {}

    @Bean
    @Profile("mather")
    public Skirt skirt() {
        return new Skirt();
    }

    public static class Skirt implements Item {}

    @Bean
    @Profile("children")
    public Toy toy() {
        return new Toy();
    }

    public static class Toy implements Item {}

    @Bean
    public Water water() {
        return new Water();
    }

    public static class Water implements Item {}

    @Bean
    @Hot
    public Cake cake() {
        return new Cake();
    }

    public static class Cake implements Item {}

    @Bean
    @Cold
    public IceCream iceCream() {
        return new IceCream();
    }

    public static class IceCream implements Item {}

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Chopsticks chopsticks() {
        return new Chopsticks();
    }

    public static class Chopsticks implements Item {}

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Bottle bottle() {
        return new Bottle();
    }

    public static class Bottle implements Item {}

}
