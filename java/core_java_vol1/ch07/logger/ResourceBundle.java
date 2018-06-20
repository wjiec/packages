package logger;

import java.util.Locale;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResourceBundle {
    public static void main(String[] args) {
        if (new Random().nextInt(10) < 5) {
            Locale.setDefault(new Locale("de"));
        }
        Logger logger = Logger.getLogger("com.shellboot.app", "logger.messages");

        logger.log(Level.WARNING, "ConnectionClosed", "socket");
        logger.log(Level.WARNING, "ConnectingTimeout", new Object[] {"socket", 3});
    }
}
