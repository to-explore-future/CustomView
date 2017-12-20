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
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.customview.R;
import com.example.customview.utils.GetScreenWH;

/**
 * Created by to-explore-future on 2017/second/23
 */
public class DreamEffectView2 extends View {
    private Bitmap mBitmap;
    private Bitmap darkCornerBitmap;
    private Paint mBitmapPaint;
    private PorterDuffXfermode mXfermode;
    public int screenHeight;
    public int screenWidth;
    private Paint mShaderPaint;

    public DreamEffectView2(Context context, AttributeSet attrs) {
        super(context, attrs);

        getScreenWH();
        // 初始化资源
        initRes(context);

        // 初始化画笔
        initPaint();
    }

    private void initRes(Context context) {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.girl);

        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SCREEN);
    }

    private void initPaint() {
        // 实例化画笔
        mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 去饱和、提亮、色相矫正
        mBitmapPaint.setColorFilter(new ColorMatrixColorFilter(new float[]{0.8587F, 0.2940F, -0.0927F, 0, 6.79F, 0.0821F, 0.9145F, 0.0634F, 0, 6.79F, 0.2019F, 0.1097F, 0.7483F, 0, 6.79F, 0, 0, 0, 1, 0}));
        // 实例化Shader图形的画笔
        mShaderPaint = new Paint();
        //暗角bitmap,这个只是生成了有宽高的bitmap,但是这个bitmap里面没有内容,
        // 根据我们源图的大小生成暗角Bitmap
        darkCornerBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        // 将该暗角Bitmap注入Canvas
        Canvas canvas = new Canvas(darkCornerBitmap);

        // 计算径向渐变半径
        float radiu = canvas.getHeight() * (2F / 3F);

        // 实例化径向渐变
        RadialGradient radialGradient = new RadialGradient(
                canvas.getWidth() / 2F, canvas.getHeight() / 2F,
                radiu, new int[]{0, 0, 0xAA000000},
                new float[]{0F, 0.7F, 1.0F}, Shader.TileMode.CLAMP);

        //给radialGradient设置matrix,改变其径向渐变,形成大部分的亮区域和少部分的暗角,原理不清楚:对matrix不了解
        // 实例化一个矩阵
        Matrix matrix = new Matrix();

        // 设置矩阵的缩放
        matrix.setScale(canvas.getWidth() / (radiu * 2F), 1.0F);

        // 设置矩阵的预平移
        matrix.preTranslate(((radiu * 2F) - canvas.getWidth()) / 2F, 0);

        radialGradient.setLocalMatrix(matrix);

        mShaderPaint.setShader(radialGradient);

        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), mShaderPaint);
    }


    private void getScreenWH() {
        DisplayMetrics screenWH = GetScreenWH.getScreenWH(getContext());
        screenHeight = screenWH.heightPixels;
        screenWidth = screenWH.widthPixels;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(screenWidth, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(screenHeight, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);

        // 新建图层
        int layer = canvas.saveLayer(0, 0, mBitmap.getWidth(), mBitmap.getHeight(), mBitmapPaint, Canvas.ALL_SAVE_FLAG);

        // 绘制混合颜色
        canvas.drawColor(0xcc1c093e);

        mBitmapPaint.setXfermode(mXfermode);

        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);

        mBitmapPaint.setXfermode(null);

        canvas.restoreToCount(layer);

        canvas.drawBitmap(darkCornerBitmap, 0, 0, null);

    }
}
