package com.example.customview.customview.canvas;

import java.util.Random;


public class RandomGenerator {

    private static final Random RANDOM = new Random();

    /**
     * get a number between the to params
     */
    public float getRandom(float lower, float upper) {
        float min = Math.min(lower, upper);
        float max = Math.max(lower, upper);
        return getRandom(max - min) + min;
    }

    /**
     * get a float number from 0.0 to upper(dose not contain upper)
     * @param upper
     * @return
     */
    public float getRandom(float upper) {
        return RANDOM.nextFloat() * upper;
    }

    /**
     * get a int number from 0 to upper(dose not contain upper)
     * @param upper
     * @return
     */
    public int getRandom(int upper) {
        return RANDOM.nextInt(upper);
    }
    
}

