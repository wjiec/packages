package logger;

import java.util.logging.Filter;
import java.util.logging.Logger;

public class LoggerFilterSample {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger("logger");
        Filter filter = new LoggerFilter();

        logger.setFilter(filter);

        logger.info("message has S");
        logger.info("message has s");
    }
}
