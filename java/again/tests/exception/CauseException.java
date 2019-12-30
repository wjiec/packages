package tests.exception;

import main.exception.FileException;
import main.exception.SqlException;
import main.exception.TimerException;

import java.util.Random;

public class CauseException {

    public static void main(String[] args) {
        try {
            mkException();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private static void mkException() throws Throwable {
        try {
            if (new Random().nextInt(2) == 0) {
                throw new FileException("2222");
            }
            throw new TimerException("11111");
        } catch (FileException e) {
            // 因为getCause返回的是Throwable类型, 所以必须使用Throwable进行声明和捕获
            throw new SqlException("exception chain").initCause(e);
        }
    }

}
