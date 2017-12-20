package com.example.customview.customview.canvas;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.graphics.Canvas;
/**
 * Created by to-explore-future on 2017/3/second
 */
public class SnowView extends View {

    private static final int NUM_SNOWFLAKES = 150;
    private static final int DELAY = 5;
    private SnowFlake[] mSnowFlakes;

    public SnowView(Context context) {
        super(context);
    }

    public SnowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SnowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw || h != oldh) {
            initSnow(w, h);
        }
    }
    //把所有的雪花生成出来
    private void initSnow(int width, int height) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);

        mSnowFlakes = new SnowFlake[NUM_SNOWFLAKES];

        for (int i = 0; i < NUM_SNOWFLAKES; ++i) {
            mSnowFlakes[i] = SnowFlake.create(width, height, paint);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (SnowFlake s : mSnowFlakes) {
            s.draw(canvas);
        }
        //get a handler from AttachInfo , invoke postDelayed (handler.sendMessageDelayed() )
        getHandler().postDelayed(runnable, DELAY);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };
}
