package logger;

import java.io.IOException;
import java.util.Random;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomLogger {
    private static final Logger rootLogger = Logger.getLogger("com.shellboot");

    public static void main(String[] args) {
        Logger childLogger = Logger.getLogger("com.shellboot.app");

        rootLogger.setLevel(Level.FINEST);
        rootLogger.info(String.format("childLogger level = %s", childLogger.getLevel()));
        //assert childLogger.getLevel() == Level.WARNING;

        // default handler level is INFO

        rootLogger.setUseParentHandlers(false);
        Handler handler = new ConsoleHandler();
        handler.setLevel(Level.FINEST);
        rootLogger.addHandler(handler);

        try {
            read(new char[10], 100);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private static int read(char[] buffer, int size) throws Throwable {
        rootLogger.entering("CustomLogger", "read", new Object[] {buffer, size});

        if (new Random().nextInt(10) < 5) {
            Throwable exception = new IOException("random error");
            rootLogger.throwing("CustomLogger", "read", exception);
            throw exception;
        }

        rootLogger.exiting("CustomLogger", "read", size);
        return size;
    }
}
