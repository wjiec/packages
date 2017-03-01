import java.awt.*;
import java.applet.*;
import javax.swing.*;

public class AppletHelloWorld extends JApplet {
    public void print(Graphics g) {
        g.drawString("Hello World", 20, 20);
    }
}