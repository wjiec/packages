package logger;

import javax.swing.*;
import java.util.Random;
import java.util.logging.Logger;

public class WindowHandlerSample {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger("logger");
        logger.setUseParentHandlers(false);

        WindowHandler handler = new WindowHandler();
        logger.addHandler(handler);

        Timer timer = new Timer(1000, e -> {
            logger.info("Hello" + new Random().nextInt());
        });
        timer.start();

        JFrame frame = new JFrame();
        frame.add(handler.output);

        frame.setSize(640, 320);
        frame.setVisible(true);
    }
}
