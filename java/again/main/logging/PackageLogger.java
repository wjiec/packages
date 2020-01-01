package main.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PackageLogger {

    public static void main(String[] args) {
        Logger root = Logger.getLogger("main");
        Logger sub = Logger.getLogger("main.logging");

        root.setLevel(Level.SEVERE);
        root.severe("root log");
        sub.severe("sub log");

        sub.setLevel(Level.INFO);
        root.info("root log");
        sub.info("sub log");

        root.setLevel(Level.WARNING);
        root.warning("root log");
        sub.warning("sub log");
        System.out.println(sub.getLevel().getName());
    }

}
