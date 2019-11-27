package tests.interfaces;

import main.interfaces.Level;

import java.util.Arrays;

public class LevelTest {

    public static void main(String[] args) {
        Level[] levels = new Level[]{
            new Level(9),
            new Level(7),
            new Level(5),
            new Level(3),
            new Level(1),
        };

        Arrays.sort(levels);
        System.out.println(Arrays.toString(levels));
    }

}
