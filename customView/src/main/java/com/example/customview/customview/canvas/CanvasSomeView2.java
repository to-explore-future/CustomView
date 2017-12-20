package com.example.customview.customview.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by to-explore-future on 2017/3/31
 */
public class CanvasSomeView2 extends View {

    private final Paint paint;
    private final Paint paint1;
    private final Paint textPaint;
    private final Path path;
    private int mViewH;
    private int mViewW;
    private float angle;
    private final Handler handler;
    private String str;

    public CanvasSomeView2(Context context, AttributeSet attrs) {
        super(context, attrs);

        str = "黑色方框就是saveLayer的区域";

        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

        paint1 = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        paint1.setStyle(Paint.Style.STROKE);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(50);

        path = new Path();
        path.moveTo(2, 2);
        path.lineTo(50000, 50000);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                invalidate();
            }
        };

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    SystemClock.sleep(2);
                    angle += 0.5;
                    handler.sendEmptyMessage(0);
                }
            }
        }.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mViewH = h;
        mViewW = w;
    }

    @Override
    protected void onDraw(Canvas canvas) {

//        canvas.drawColor(Color.GREEN);
//        canvas.save();
        //这个的底层就是一个bitmap,给多大的参数就是多么大的bitmap
        canvas.saveLayer(100, 100, 900, 900, paint, Canvas.ALL_SAVE_FLAG);
        canvas.rotate(angle);
        paint.setColor(Color.GREEN);
        canvas.drawRect(200, 200, 800, 800, paint);
        canvas.restore();

        canvas.drawRect(100, 100, 900, 900, paint1);

        canvas.drawText(str, 200, 600, textPaint);

        canvas.drawPath(path, paint1);

    }
}
