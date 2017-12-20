package com.example.customview.customview.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by to-explore-future on 2017/3/31
 */
public class CanvasSomeView1 extends View {


    private final Paint redPaint;
    private int mViewH;
    private int mViewW;
    private Paint mPaint;
    private Paint yellowPaint;

    public CanvasSomeView1(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(Color.BLUE);

        redPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        redPaint.setColor(Color.RED);

        yellowPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        yellowPaint.setColor(Color.YELLOW);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mViewH = h;
        mViewW = w;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //在save之前,画布是没有旋转的save就是save之前的状态,
        canvas.save();

        canvas.rotate(30);
        canvas.drawRect(0,0,mViewW,mViewH,mPaint);
        canvas.drawRect(200,400,800,1000, redPaint);

        canvas.restore();
        //save和restore之间的部分相当于一个虚拟的空间,无论这个空间怎么操作都不会对save和restore之外的真实的空间造成影响.

        canvas.drawRect(200, 400, 800, 1000, yellowPaint);
    }

}
