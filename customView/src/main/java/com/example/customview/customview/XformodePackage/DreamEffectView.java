package com.example.customview.customview.XformodePackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.customview.R;
import com.example.customview.utils.GetScreenWH;

/**
 * Created by to-explore-future on 2017/second/22
 */
public class DreamEffectView extends View {

    private Paint paint;
    private Bitmap girl;
    private PorterDuffXfermode xFermode;
    private Rect rect;
    private Paint shaderPaint;
    private Bitmap darkCornerGirl;
    private Canvas canvas;
    private int radius;
    private Paint mShaderPaint;

    public DreamEffectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public int screenHeight;
    public int screenWidth;

    private void init() {
        getScreenWH();

        girl = BitmapFactory.decodeResource(getResources(), R.mipmap.girl);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        paint.setColor(Color.RED);
        paint.setTextSize(50);
        //让画笔具备色彩过滤的功能
        paint.setColorFilter(new ColorMatrixColorFilter(
                new float[]{0.8587F, 0.2940F, -0.0927F, 0, 6.79F, 0.0821F, 0.9145F, 0.0634F,
                        0, 6.79F, 0.2019F, 0.1097F, 0.7483F, 0, 6.79F, 0, 0, 0, 1, 0}));

        xFermode = new PorterDuffXfermode(PorterDuff.Mode.SCREEN);

        rect = new Rect(0, girl.getHeight() * 2, girl.getWidth(), girl.getHeight() * 3);
        shaderPaint = new Paint();
        shaderPaint.setShader(new RadialGradient(girl.getWidth() / 2, girl.getHeight() * 2 + girl
                .getHeight() / 2, girl.getHeight() / 8 * 7, Color.TRANSPARENT, Color.BLACK, Shader.TileMode.CLAMP));



    }

    private void getScreenWH() {
        DisplayMetrics screenWH = GetScreenWH.getScreenWH(getContext());

        screenHeight = screenWH.heightPixels;
        screenWidth = screenWH.widthPixels;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(screenWidth, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(screenHeight * 2, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //在A图层
        canvas.drawBitmap(girl, 0, girl.getHeight(), paint);
        canvas.drawText("先画出来的,在最开始的图层上,比如:A图层", 0, girl.getHeight() + 50, paint);


        //在layer图层
        //保存一个图层,那么后续的代码实际上是画在了这个保存的图层上
        int layer = canvas.saveLayer(0, 0, girl.getWidth(), girl.getHeight(), null, Canvas
                .ALL_SAVE_FLAG);

        canvas.drawColor(0xcc1c093e);

        paint.setXfermode(xFermode);

        canvas.drawBitmap(girl, 0, 0, paint);

        paint.setXfermode(null);

        canvas.drawText("后画出来的,在A图层上面", 0, 0 + 50, paint);
        canvas.drawText("这张看起来更自然", 0, 0 + 100, paint);

        canvas.restoreToCount(layer);

        //实现一个暗角效果
        int layer3 = canvas.saveLayer(0, girl.getHeight() * 2, girl.getWidth(), girl.getHeight() * 3, null, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(girl, 0, girl.getHeight()*2, paint);
        //图片有了,这次直接在图片画一个渐变的长方形
        canvas.drawRect(rect, shaderPaint);
        canvas.drawText("一个中心透明向四周逐渐变黑的正", 0, girl.getHeight()*2 + 50, paint);
        canvas.drawText("方形和一个图片的组合", 0, girl.getHeight()*2 + 100, paint);
        canvas.restoreToCount(layer3);



    }
}
