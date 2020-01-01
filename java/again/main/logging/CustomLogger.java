package main.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomLogger {

    public static void main(String[] args) {
        Logger logger = Logger.getLogger("main.logging");
        logger.finest("This is a finest log");
        logger.finer("This is a finer log");
        logger.fine("This is a fine log");

        logger.setLevel(Level.FINEST);
        logger.info("This is a log");

        logger.setLevel(Level.WARNING);
        logger.info("This is a log again");

        logger.setLevel(Level.FINEST);
        logger.finest("This is a finest log");
        logger.finer("This is a finer log");
        logger.fine("This is a fine log");
        logger.severe("This is a log");

    }

}
