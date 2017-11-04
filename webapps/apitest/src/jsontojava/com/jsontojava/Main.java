package jsontojava.com.jsontojava;


import java.io.*;
import java.sql.SQLException;
import java.util.HashMap;

public class Main {


    public static void main(String[] args) {

        //二进制字面量,只要在二进制数值前面加 0b或者0B
        byte nByte = (byte) 0b0001;
        short nShort = (short) 0B0010;
        int nInt = 0b0011;
        long nLong = 0b0100L;

        //数字字面量可以出现下划线
        int a = 10_0000_0000;//1000000000
        long b = 0xffff_ffff_ffff_ffffl; // -1
        byte c = 0b0001_1000; //24
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);

        //try catch
        try {
            testthrows();
        } catch (IOException | SQLException e) {

        }

    }

    public static void testthrows() throws IOException, SQLException {
    }



    //switch 语句可以用字符串了
    private static void switchString(String str) {
        switch (str) {
            case "one":
                System.err.println("1");
                break;
            case "two":
                System.out.println("2");
                break;
            default:
                System.out.println("err");
        }
    }
}

