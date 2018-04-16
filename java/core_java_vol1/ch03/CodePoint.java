public class CodePoint {
    public static void main(String[] args) {
        String message = "Hello, \u8f6f\u59b9\u5b50";

        System.out.printf("message.length = %d\n", message.length());
        for (int i = 0; i < message.codePointCount(0, message.length()); ++i) {
            System.out.printf("%c ", message.charAt(i));
        }
        System.out.println();

        int[] codePoints = message.codePoints().toArray();
        for (int cp : codePoints) {
            System.out.printf("%d ", cp);
        }
        System.out.println();
    }
}
