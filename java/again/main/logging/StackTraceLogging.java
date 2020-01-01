package main.logging;

import java.util.logging.Logger;

// -Djava.util.logging.config.file=src/main/logging/logging.properties
public class StackTraceLogging {

    private static Logger logger;

    static {
        logger = Logger.getLogger("main.logging");
    }

    public static void main(String[] args) {
        new StackTraceLogging().trace(1, 2);
    }

    private void trace(int i, int j) {
        logger.entering("StackTraceLogging", "trace", new Object[]{i, j});

        System.out.printf("%d + %d = %d\n", i, j, i + j);
        logger.info("i + j = " + (i + j));

        logger.exiting("StackTraceLogging", "trace", i + j);
    }

}
