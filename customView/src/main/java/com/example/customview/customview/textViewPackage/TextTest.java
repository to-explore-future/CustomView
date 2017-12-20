package com.example.customview.customview.textViewPackage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.example.customview.utils.GetScreenWH;

/**
 * Created by to-explore-future on 2017/second/first
 */
public class TextTest extends View {
    private String text = "我是中国人";

    private Paint paint;
    private int width;
    private int height;
    private Paint.FontMetrics fontMetrics;

    public TextTest(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);//blitting when dithering
        // 抖动的时候渐变,防抖动
        paint.setTextSize(150);



        DisplayMetrics displayMetrics = GetScreenWH.getScreenWH(getContext());
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;

        //在字体还没有画之前我们就可以知道 一下的属性,可以根据这些属性算出其他行应该画在什么位置
        fontMetrics = paint.getFontMetrics();
        Log.i("fontMetrics", "leading:" + fontMetrics.leading);
        Log.i("fontMetrics", "top:" + fontMetrics.top);
        Log.i("fontMetrics", "ascent:" + fontMetrics.ascent);
        Log.i("fontMetrics", "descent:" + fontMetrics.descent);
        Log.i("fontMetrics", "bottom:" + fontMetrics.bottom);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(text, 0, Math.abs(fontMetrics.top), paint);
    }
}
