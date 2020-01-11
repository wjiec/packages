package tests.generic.helper;

import main.generic.erased.Pair;
import main.generic.helper.Maker;

public class MakerTest {

    public static void main(String[] args) {
        try {
            Pair<String> pair = Maker.make(String.class);
            System.out.println(pair.getFirst());
            System.out.println(pair.getSecond());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
