public class ToStringSample {
    private String name;

    private int count;

    private ToStringSample(String name, int count) {
        this.name = name;
        this.count = count;
    }

    @Override
    public String toString() {
        return String.format("%s[name=%s, count=%d]", getClass().getName(), name, count);
    }

    public static void main(String[] args) {
        ToStringSample toStringSample = new ToStringSample("Jack", 1);
        System.out.println(toStringSample);
    }
}
