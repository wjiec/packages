package logger;


import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LoggerFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
        return String.format("%s\t->\t%s\n", record.getLevel(), record.getMessage());
    }
}
