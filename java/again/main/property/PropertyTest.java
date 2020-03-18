package main.property;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyTest {

    public static void main(String[] args) {
        Properties properties0 = new Properties();
        properties0.setProperty("width", "640");
        properties0.setProperty("height", "320");
        System.out.println(properties0);

        try (FileOutputStream stream = new FileOutputStream("app.properties")) {
            properties0.store(stream, "properties comments");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Properties properties1 = new Properties();
        try (FileInputStream stream = new FileInputStream("app.properties")) {
            properties1.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(properties1);
    }

}
