package com.example.customview.utils;

/**
 * Created by to-explore-future on 2017/first/20 15:14
 * 这个类用来把int类型的颜色值转换为ARGB格式,或者是转换回去
 */
public class ColorTransformUtils {
    /**
     * 把一个ARGB格式的color值转变成一个int类型的color值
     */
    public static int ARGB_ColorToInt(int a,int r,int g,int b){
        int tempColor = 0;
        int intSet[] = {b, g, r, a};
        for (int i = 0; i < 4; i++) {
            int temp = intSet[i] & 0b11111111;  //去后八位
            temp = temp << i * 8;               //放到指定的位置
            tempColor += temp;                  //组后合并
        }
        return tempColor;
    }

    public static int[] int_colorToARGB(int color) {
        int a = 0 ;
        int r = 0;
        int g = 0;
        int b = 0;
        int colors[] = {b,g,r,a};
        for (int i = 0; i < 4; i++) {
            colors[i] = color & 0b11111111;
            color >>>= 8;
        }
        return colors;
    }


}
