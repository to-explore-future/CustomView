package com.example.customview.customview.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.customview.utils.RandomUtils;

/**
 * Created by to-explore-future on 2017/3/second
 */
public class MySnowView extends View {

    private Snow[] snowSets;
    private int num_snow = 50;     //this decided the snow is light or heavy. By changing the nubmer to change the size of snow.
    private Paint paint;


    private float bigSnow = 20f;
    private float smallSnow = 5f;

    private int delayTime = 5;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };


    public MySnowView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w != oldw || h != oldh) {
            init(w, h);
        }
    }

    private void init(float width, float height) {

        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);

        snowSets = new Snow[num_snow];

        for (int i = 0; i < snowSets.length; i++) {

            //这个雪创建在屏幕中,不能跑到屏幕的外边,所以需要屏幕的参数
            float x = RandomUtils.getRandom(0f, width);
            float y = RandomUtils.getRandom(0f, height);
            float snowRadius = RandomUtils.getRandom(smallSnow, bigSnow);


            Snow snow = new Snow(x, y, snowRadius);
            snowSets[i] = snow;
        }


    }


    @Override
    protected void onDraw(Canvas canvas) {

        for (int i = 0; i < snowSets.length; i++) {
            snowSets[i].draw(canvas, paint);
        }

        getHandler().postDelayed(runnable, delayTime);
    }
}
