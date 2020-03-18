package main.prefeerence;

import java.io.IOException;
import java.util.Arrays;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class PreferenceTest {

    public static void main(String[] args) {
        try {
            for (String key : Preferences.userRoot().keys()) {
                System.out.println("UserRoot: " + key);
            }

            for (String key : Preferences.systemRoot().keys()) {
                System.out.println("UserRoot: " + key);
            }
        } catch (BackingStoreException e) {
            e.printStackTrace();
        }

        Preferences userRoot = Preferences.userRoot();
        Preferences jetbrains = userRoot.node("JetBrains");
        System.out.println(jetbrains);
        Preferences userId = jetbrains.node("UserIdOnMachine");
        try {
            System.out.println(Arrays.toString(userId.keys()));
            userId.exportSubtree(System.out);
        } catch (BackingStoreException | IOException e) {
            e.printStackTrace();
        }

        Preferences preferences = Preferences.userNodeForPackage(Integer.valueOf(10).getClass());
        System.out.println(preferences);
    }

}
