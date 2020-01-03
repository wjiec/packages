package tests.generic;

import main.generic.MinSerializer;

public class MinSerializerTest {

    public static void main(String[] args) {
        System.out.println(MinSerializer.min(new Double[]{ 2.33, -1., 6.66, 1e-4, -2.33e5}));
    }

}
