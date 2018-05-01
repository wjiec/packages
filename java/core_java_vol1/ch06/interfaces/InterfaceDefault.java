package interfaces;

public interface InterfaceDefault {
    default int compare() {
        return 0;
    }

    default double getPrice() {
        return 0;
    }
}
