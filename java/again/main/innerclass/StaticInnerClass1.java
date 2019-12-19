package main.innerclass;

public class StaticInnerClass1 {

    public static class Pair {

        private final int first;

        private final int second;

        public Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }

        public int getFirst() {
            return first;
        }

        public int getSecond() {
            return second;
        }

    }

}
