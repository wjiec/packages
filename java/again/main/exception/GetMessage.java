package main.exception;

public class GetMessage {

    public static void main(String[] args) {
        try {
            throw new CustomException("hello");
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
    }

}
