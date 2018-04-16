import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileReader {
    public static void main(String[] args) throws IOException {
        String currentPath = System.getProperty("user.dir");
        System.out.printf("Current Path: %s\n\n\n", currentPath);

        Scanner scanner = new Scanner(Paths.get("src/FileReader.java"));
        while (scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
        }
    }
}
