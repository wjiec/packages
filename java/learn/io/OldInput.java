import java.io.*;

public class OldInput {
    public static void main(String[] args) {
        char ch = '\0';

        try {
            ch = (char)System.in.read();
        } catch (IOException e) { }
        System.out.printf("%c\n", ch);

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.println(in.readLine());
        } catch (IOException e) {}
    }
}
