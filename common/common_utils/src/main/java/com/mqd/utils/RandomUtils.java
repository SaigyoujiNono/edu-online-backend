package com.mqd.utils;

import java.util.Random;

public class RandomUtils {
    static private final Random random = new Random(System.currentTimeMillis());
    //生产一个范围内int随机数
    public static int randomInt(int min, int max) {
        if (min == max){
            return max;
        }
        if (max < min){
            return 0;
        }
        return random.nextInt(max-min+1)+min;
    }
}
