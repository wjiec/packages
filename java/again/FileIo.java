import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileIo {

    public static void main(String[] args) throws IOException {
        System.out.println("WorkDir: " + System.getProperty("user.dir"));
        Scanner scanner = new Scanner(Paths.get("src/FileIo.java"));
        while (scanner.hasNext()) {
            System.out.println(scanner.nextLine());
        }

        PrintWriter writer = new PrintWriter("tmp.java", StandardCharsets.UTF_8);
        // reset is not supported
        for (scanner.reset(); scanner.hasNext();) {
            writer.println(scanner.nextLine());
        }

        // empty file
        writer.flush();
        writer.close();
    }

}
