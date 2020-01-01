package main.logging;

import java.util.logging.Logger;

public class GlobalLogger {

    public static void main(String[] args) {
        Logger.getGlobal().info("This is a log");
        Logger.getGlobal().info("This is a log again");
    }

}
