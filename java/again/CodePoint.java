public class CodePoint {

    public static void main(String[] args) {
        String string = "𝕆 Hello";
        System.out.println("length = " + string.length()); // 8
        System.out.println("code point = " + string.codePointCount(0, string.length()));
        System.out.println();

        System.out.println(string.charAt(0));
        System.out.println(string.charAt(1));
        System.out.println(string.charAt(2));
        System.out.println(string.charAt(3));
        System.out.println();

        System.out.println(string.codePointAt(0));
        System.out.println(string.codePointAt(1));
        System.out.println(string.codePointAt(2));
        System.out.println(string.codePointAt(3));
        System.out.println();

        int index = string.offsetByCodePoints(0, 0);
        System.out.println(string.codePointAt(index));
        System.out.println();

        index = string.offsetByCodePoints(0, 1);
        System.out.println(string.codePointAt(index)); // 120134 = 1d546
        System.out.println();

        int[] points = string.codePoints().toArray();
        for (int point : points) {
            System.out.print(point + " ");
        }
        System.out.println();
    }

}
