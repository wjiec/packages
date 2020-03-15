package main.collection.algo;

import java.util.BitSet;

public class BitSetTest {

    public static void main(String[] args) {
        final int max = 1_0000_0000;
        BitSet bitSet = new BitSet(max + 1);

        long start = System.currentTimeMillis();
        for (int i = 0; i < max + 1; ++i) {
            bitSet.set(i);
        }
        System.out.println("all set: " + (System.currentTimeMillis() - start));

        for (int i = 2; i * i < max; ++i) {
            if (bitSet.get(i)) {
                for (int j = i * 2; j < max; j += i) {
                    bitSet.clear(j);
                }
            }
        }

        int count = 0;
        for (int i = 2; i < max; ++i) {
            if (bitSet.get(i)) {
                ++count;
            }
        }
        long end = System.currentTimeMillis();

        System.out.println("count: " + count + ", time: " + (end - start));
    }

}
