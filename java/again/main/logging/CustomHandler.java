package main.logging;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomHandler {

    public static void main(String[] args) {
        Logger logger = Logger.getLogger("main.logging");
        logger.setLevel(Level.FINEST);
        logger.setUseParentHandlers(false);

        Handler handler = new ConsoleHandler();
        handler.setLevel(Level.FINEST);
        logger.addHandler(handler);

        logger.finest("This is a custom log from custom logger and handler");
    }

}
