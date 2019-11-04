public class BreakTag {

    public static void main(String[] args) {
        System.out.println("Loop #0");
        loop0:
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < i; ++j) {
                if (j == 5) {
                    break loop0;
                }
            }
        }

        System.out.println("Loop #1");
        loop1:
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < i; ++j) {
                if (j == 3) {
                    break loop1;
                }
            }
        }
    }

}
