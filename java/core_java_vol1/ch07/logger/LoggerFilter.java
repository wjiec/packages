package logger;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

public class LoggerFilter implements Filter {
    @Override
    public boolean isLoggable(LogRecord record) {
        return record.getMessage().contains("S");
    }
}
