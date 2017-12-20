package com.example.customview.utils;

import android.graphics.Point;
import android.graphics.PointF;

/**
 * Created by to-explore-future on 2017/4/8
 */
public class LineUtil {

    /**
     * Two point generate a line
     * 直线方程表达式:y = mx + n;
     * 假设 两个点的坐标为: A(a,b) B(c,d)
     * 经过推算 : m = ( b - d ) / ( c + a ) ;  n = b - m * a;
     */
    public static float[] getLineParam(PointF pointA, PointF pointB) {

        float a = pointA.x;
        float b = pointA.y;
        float c = pointB.x;
        float d = pointB.y;

        float m = (b - d) / (c + a);
        float n = b - m * a;
        float[] lineParam = new float[]{m, n};

        return lineParam;
    }
}
