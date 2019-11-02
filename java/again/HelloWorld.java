public class HelloWorld {

    public static void main(String[] args) {
        String greet = "Hello World!";
        System.out.println(greet);

        for (int i = 0; i < greet.length(); i++) {
            System.out.print("=");
        }
        System.out.println();
    }

}
