package logger;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GlobalLogger {
    public static void main(String[] args) {
        Logger.getGlobal().info("Open a socket connection to github.com");

        Logger.getGlobal().setLevel(Level.OFF);
        Logger.getGlobal().warning("connecting github.com timeout, try again");

        Logger.getGlobal().setLevel(Level.WARNING);
        Logger.getGlobal().info("connecting to github.com secondly");
        Logger.getGlobal().warning("connecting github.com timeout, connection closed");
    }
}
