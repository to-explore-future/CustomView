package com.example.customview.bean;

import android.graphics.PointF;

/**
 * Created by to-explore-future on 2017/3/10
 * Record the corresponding points and tags.
 */
public class SignsAndPoints {

    public SignsAndPoints(int sign, PointF point) {
        this.sign = sign;
        this.point = point;
    }

    private int sign;
    private PointF point;

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    public PointF getPoint() {
        return point;
    }

    public void setPoint(PointF point) {
        this.point = point;
    }
}
