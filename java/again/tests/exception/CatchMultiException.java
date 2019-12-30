package tests.exception;

import main.exception.FileException;
import main.exception.SqlException;
import main.exception.TimerException;

import java.util.Random;

public class CatchMultiException {

    public static void main(String[] args) {
        try {
            switch (new Random().nextInt(3)) {
                case 0:
                    throw new SqlException("0");
                case 1:
                    throw new FileException("1");
                case 2:
                    throw new TimerException("2");
            }
        } catch (SqlException | TimerException e) {
            e.printStackTrace();
        } catch (FileException e) {
            System.out.println(e.getMessage());
        }
    }

}
