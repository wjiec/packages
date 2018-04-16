public class StringBuild {
    public static void main(String[] args) {
        StringBuilder builder = new StringBuilder();

        builder.append("Hel");
        builder.append("lo");
        System.out.println(builder.toString());

        builder.append(',');
        builder.append(' ');
        builder.appendCodePoint(36719);

        System.out.println(builder.toString());
    }
}