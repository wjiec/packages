import java.util.Objects;

public class HashCodeSample {
    private String name;

    private int count;

    private HashCodeSample(String name, int count) {
        this.name = name;
        this.count = count;
    }

    @Override
    final public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        HashCodeSample hashCodeSample = (HashCodeSample) obj;
        return Objects.equals(name, hashCodeSample.name) && count == hashCodeSample.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, count);
    }

    public static void main(String[] args) {
        HashCodeSample hashCodeSample1 = new HashCodeSample("Jack", 1);
        HashCodeSample hashCodeSample2 = new HashCodeSample("John", 2);

        System.out.println(hashCodeSample1.equals(hashCodeSample2));
        System.out.println("hashCodeSample1: " + hashCodeSample1.hashCode());
        System.out.println("hashCodeSample2: " + hashCodeSample2.hashCode());

        HashCodeSample copyHashCodeSample1 = new HashCodeSample("Jack", 1);
        System.out.println(hashCodeSample1.equals(copyHashCodeSample1));
        System.out.println("hashCodeSample1: " + hashCodeSample1.hashCode());
        System.out.println("copyHashCodeSample1: " + copyHashCodeSample1.hashCode());
    }
}
