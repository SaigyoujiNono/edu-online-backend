package com.mqd.utils;

public class ValidateCode {
    private final static String[] candidateCode = {
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
            "q", "r", "s", "t", "u", "v", "w", "x", "y", "z","1","2","3","4","5","6","7","8","9","0",
    };
    public static String getValidate(){
        StringBuilder res = new StringBuilder();
        for (int i = 0; i <6; i++) {
            res.append(candidateCode[RandomUtils.randomInt(0,candidateCode.length-1)]);
        }
        return res.toString();
    }
}
