package com.example.customview.customview.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by to-explore-future on 2017/second/27
 */
public class CanvasClip2View extends View {

    private final Rect rect;
    private final Paint paint;
    private final Rect rect1;
    private final Paint textPaint;

    private boolean showUp;

    public CanvasClip2View(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(5);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(35);
        textPaint.setColor(Color.RED);

        rect = new Rect(10, 10, 500, 500);
        rect.intersect(250, 250, 750, 750);         //这个rect的值已经变成了 (250,250,500,500)

        rect1 = new Rect(10, 1000, 500, 1500);
        rect1.union(250, 1250, 750, 1750);


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.RED);

        canvas.drawRect(10, 10, 500, 500, paint);
        canvas.drawRect(250, 250, 750, 750, paint);


        canvas.drawRect(10, 1000, 500, 1500, paint);
        canvas.drawRect(250, 1250, 750, 1750, paint);


        if (showUp) {
            canvas.clipRect(rect);
        } else {
            canvas.clipRect(rect1);
        }


        canvas.drawColor(Color.BLUE);

        canvas.drawText("rect.intersect()", 260, 380, textPaint);
        canvas.drawText("rect.union()", 250, 1380, textPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        if (x < 751) {
            float y = event.getY();
            if (y > 1250) {
                showUp = false;
            } else if (y < 750) {
                showUp = true;
            }
            invalidate();
        }

        return true;
    }
}
