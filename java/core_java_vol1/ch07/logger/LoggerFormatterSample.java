package logger;

import javax.swing.*;
import java.util.Random;
import java.util.logging.Formatter;
import java.util.logging.Logger;

public class LoggerFormatterSample {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger("logger");
        WindowHandler handler = new WindowHandler();
        Formatter formatter = new LoggerFormatter();

        logger.setUseParentHandlers(false);
        handler.setFormatter(formatter);
        logger.addHandler(handler);

        JFrame frame = new JFrame();
        frame.add(handler.output);

        Timer timer = new Timer(1000, e -> {
            logger.info(String.format("randome number = %d", new Random().nextInt()));
        });
        timer.start();

        frame.setSize(640, 320);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
