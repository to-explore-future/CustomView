package com.example.customview.utils;

import java.util.Random;

/**
 * Created by to-explore-future on 2017/3/3
 */
public class RandomUtils {

    private static Random random = new Random();


    /**
     * return a float number between start and end.inlcude start ,dos't include end.
     */
    public static float getRandom(float start, float end) {
        return random.nextFloat() * (end - start) + start;
    }

}
