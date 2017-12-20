package com.example.customview.customview.textViewPackage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.customview.utils.GetScreenWH;

/**
 * Created by to-explore-future on 2017/second/first
 * 这个让text画到屏幕的正中间
 */
public class TextDrawAnywhere extends View {
    private String text = "我是p中国人";
    private String align_left = "左对齐";
    private String align_center = "居中对齐";
    private String align_right = "右对齐";

    private Paint paint;
    private int screenW;
    private int screenH;
    private Paint.FontMetrics fontMetrics;
    private float centerY;
    private float centerX;
    private RectF rectF;
    private Paint linePaint;
    private int trueTop;
    private int trueAscent;
    private int trueDescent;
    private int trueBottom;
    private Paint paint1;
    private Paint paint2;
    private Paint paint3;

    public TextDrawAnywhere(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);//dithering when blitting
        // 抖动的时候渐变,防抖动
        paint.setTextSize(100);
        paint.setStyle(Paint.Style.STROKE);


        paint1 = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);//dithering when blitting
        paint1.setTextSize(100);
        paint1.setTextAlign(Paint.Align.CENTER);

        paint2 = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);//dithering when blitting
        paint2.setTextSize(100);
        paint2.setTextAlign(Paint.Align.LEFT);

        paint3 = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);//dithering when blitting
        paint3.setTextSize(100);
        paint3.setTextAlign(Paint.Align.RIGHT);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(Color.RED);


        DisplayMetrics displayMetrics = GetScreenWH.getScreenWH(getContext());
        screenW = displayMetrics.widthPixels;
        screenH = displayMetrics.heightPixels;

        rectF = new RectF(0, 0, screenW, screenH);


        fontMetrics = paint.getFontMetrics();

        centerX = (screenW - paint.measureText(text)) / 2;
        int baseLine = screenH / 2;
        int tempTop = (int) Math.abs(fontMetrics.top);
        trueTop = baseLine - tempTop;

        int tempAscent = (int) Math.abs(fontMetrics.ascent);
        trueAscent = baseLine - tempAscent;

        int tempDescent = (int) Math.abs(fontMetrics.descent);
        trueDescent = baseLine + tempDescent;

        int tempBottom = (int) Math.abs(fontMetrics.bottom);
        trueBottom = baseLine + tempBottom;

        centerY = screenH / 2;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(screenW, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(screenH, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawText(align_left, 0, centerY - (trueBottom - trueTop), paint2);

        canvas.drawText(text, centerX, centerY, paint);

        canvas.drawText(align_center, canvas.getWidth() / 2, centerY + (trueBottom - trueTop), paint1);

        canvas.drawText(align_right, canvas.getWidth(), centerY + (trueBottom - trueTop) * 2, paint3);

        canvas.drawLine(0, trueTop, screenW, trueTop, linePaint);
        canvas.drawLine(0, trueAscent, screenW, trueAscent, linePaint);
        canvas.drawLine(0, screenH / 2, screenW, screenH / 2, linePaint);
        canvas.drawLine(0, trueDescent, screenW, trueDescent, linePaint);
        canvas.drawLine(0, trueBottom, screenW, trueBottom, linePaint);

        canvas.drawRect(rectF, paint);
    }
}
