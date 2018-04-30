package interfaces;

import java.util.Arrays;
import java.util.Objects;

public class InterfaceSample implements Comparable<InterfaceSample> {
    public static void main(String[] args) {
        InterfaceSample[] arrays = {
            new InterfaceSample(1),
            new InterfaceSample(3),
            new InterfaceSample(8),
            new InterfaceSample(5),
            new InterfaceSample(2)
        };

        Arrays.sort(arrays);
        for (InterfaceSample s : arrays) {
            System.out.println(s);
        }
    }

    private InterfaceSample(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return String.format("%s[count=%d]", getClass().getName(), count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(count);
    }

    private int count;

    @Override
    public int compareTo(InterfaceSample o) {
        return Integer.compare(count, o.count);
    }
}
