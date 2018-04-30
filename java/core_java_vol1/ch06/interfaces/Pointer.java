package interfaces;

public interface Pointer extends Moveable, Resetable {
    public void line(int x, int y, int ...others);
}
