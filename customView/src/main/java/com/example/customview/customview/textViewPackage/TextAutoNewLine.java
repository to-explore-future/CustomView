package com.example.customview.customview.textViewPackage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.example.customview.utils.GetScreenWH;

/**
 * Created by to-explore-future on 2017/second/second
 * 这个类就是文字自动换行的基本的原理,
 */
public class TextAutoNewLine extends View {

    private TextPaint textPaint;
    private String text = "baselineShift,bgColor,density,drawableState,linkColor，这些属性都很简单大家顾名思义或者自己去尝试下即可这里就不多说了，那么这支笔有何用呢？最常用的用法是在绘制文本时能够实现换行绘制！在正常情况下Android绘制文本是不能识别换行符之类的标识符的，这时候如果我们想实现换行绘制就得另辟途径使用StaticLayout结合TextPaint实现换行，StaticLayout是android.text.Layout的一个子类，很明显它也是为文本处理量身定做的，其内部实现了文本绘制换行的处理，该类不是本系列重点我们不再多说直接Look一下它是如何实现换行的";
    private int screenW;
    private int screenH;
    private StaticLayout staticLayout;
    private TextPaint textPaint2;

    public TextAutoNewLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        //TextPaint 这个类是专门用来写字的 能够自动换行
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(50);
        textPaint.setUnderlineText(true);
        textPaint.setTypeface(Typeface.create(Typeface.SERIF, Typeface.NORMAL));
//        Typeface typeface = Typeface.createFromAsset(context.getAssert,"kt.ttf")

        textPaint2 = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        textPaint2.setColor(Color.BLUE);
        textPaint2.setTextSize(50);
        textPaint2.setUnderlineText(false);
        textPaint2.setTypeface(Typeface.create(Typeface.SERIF, Typeface.BOLD));
        textPaint2.setTextSkewX(-0.25f);
        textPaint2.setTextScaleX(1.0f);//默认是1.0f,小于或者大于1.0f字体会在原来大小的基础上,缩小或者放大
        textPaint2.setSubpixelText(true);//让文本已最优的方式显示
        textPaint2.setUnderlineText(true);
        textPaint2.setStrikeThruText(true);
        textPaint2.setLinearText(true);//每个文字绘制到屏幕上都是需要一个bitmap缓存,这样就会浪费内存,
        textPaint2.setFakeBoldText(true);

        DisplayMetrics displayMetrics = GetScreenWH.getScreenWH(getContext());
        screenW = displayMetrics.widthPixels;
        screenH = displayMetrics.heightPixels;

        float fontSpacing = textPaint.getFontSpacing();

        Log.i("method", "fontSpacing:" + fontSpacing);
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
//
//        staticLayout = new StaticLayout(text, textPaint, canvas.getWidth(), Layout.Alignment
//                .ALIGN_NORMAL, first.0f, 0.0f, false);
//        staticLayout.draw(canvas);

        StaticLayout staticLayout2 = new StaticLayout(text, textPaint2, canvas.getWidth(), Layout
                .Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        staticLayout2.draw(canvas);

        canvas.save();
        canvas.restore();
    }


}
