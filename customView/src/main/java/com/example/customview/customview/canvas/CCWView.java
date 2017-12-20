package com.example.customview.customview.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by to-explore-future on 2017/3/9
 * just display what's CCW.
 */
public class CCWView extends View {

    private String str;
    private Paint paint;
    private Path path;
    private Path path1;
    private Paint textPaint;

    public CCWView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    private void init() {
        str = "我减肥三等奖覅万农村三等奖佛都说的农夫三季度覅i就哦你金佛i物极将返";

        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        paint.setTextSize(50);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setColor(Color.GREEN);
        textPaint.setTextSize(50);

        path = new Path();
        RectF rect1 = new RectF(200, 200, 800, 800);
        path.addOval(rect1, Path.Direction.CCW);

        path1 = new Path();
        RectF rect2 = new RectF(200, 1000, 800, 1600);
        path1.addOval(rect2, Path.Direction.CW);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawTextOnPath(str, path, 0, 0, paint);

        canvas.drawPath(path, paint);

        canvas.drawText("ccw:counter clock wise", 500, 100, textPaint);

        canvas.drawTextOnPath(str, path1, 0, 0, paint);

        canvas.drawPath(path1, paint);

        canvas.drawText("cw:clock wise", 500, 900, textPaint);


    }
}
