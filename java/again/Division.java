public class Division {

    public static /* strictfp */ void main(String[] args) {
        int result0 = 1 / 2;
        System.out.println(result0);

        double result1 = 1 / 2.0;
        System.out.println(result1);

        try {
            int result3 = 1 / 0;
            System.out.println(result3);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        double result4 = 1.0 / 0;
        System.out.println(result4);
        System.out.println(Double.isInfinite(result4));
        System.out.println(Double.isFinite(result4));
        System.out.println(Double.isNaN(result4));
    }

}
