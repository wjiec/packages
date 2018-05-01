package interfaces;

public class InterfaceDefaultSample implements InterfaceDefault {
    public static void main(String[] args) {
        InterfaceDefaultSample sample = new InterfaceDefaultSample();
        System.out.println(sample.compare());
        System.out.println(sample.getPrice());
    }
}
