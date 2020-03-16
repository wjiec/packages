package main.gui;

import javax.swing.*;
import java.awt.*;

public class SimpleFrame extends JFrame {

    private final static int DEFAULT_WINDOW_WIDTH = 640;

    private final static int DEFAULT_WINDOW_HEIGHT = 320;

    public SimpleFrame() {
        setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
        setMinimumSize(new Dimension(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT));
    }

}
