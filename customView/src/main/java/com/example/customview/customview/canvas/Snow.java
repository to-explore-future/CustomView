package com.example.customview.customview.canvas;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.customview.utils.RandomUtils;

/**
 * Created by to-explore-future on 2017/3/second
 */
public class Snow {


    private float x;
    private float y;
    private float snowRadius;


    /**
     * give the param when create Snow.
     */
    public Snow(float pX, float pY, float snowRadius) {

        x = pX;
        y = pY;

        this.snowRadius = snowRadius;

    }


    //draw the snow , In fact is to draw a circle.
    public void draw(Canvas canvas, Paint paint) {
        move();
        canvas.drawCircle(x, y, snowRadius, paint);

    }

    /**
     * change the everySnow's position
     */
    public void move() {

        float offSetX = RandomUtils.getRandom(-1f, 0f);

        float offSetY = RandomUtils.getRandom(0f, 2.0f);

//        Log.i("offset", "offsetX:" + offSetX + "\toffsetY:" + offSetY);

        x = x + offSetX;
        y = y + offSetY;
    }
}
