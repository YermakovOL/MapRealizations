package org.example;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;

public class Helper {

    public static String generateRandomString(){
        return new BigInteger(130, new SecureRandom()).toString(10);
    }
    public static void printMessage(String string){
        System.out.println(string);
    }
    public static void logException(Exception e){
        System.err.print(Arrays.toString(e.getStackTrace())+"\n");
    }
}