package com.example.customview.customview.XformodePackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.example.customview.R;

/**
 * Created by to-explore-future on 2017/first/23 15:31
 */
public class EraserXfermode extends View {

    private static final int MIN_MOVE_DIS = 5;// 最小的移动距离：如果我们手指在屏幕上的移动距离小于此值则不会绘制
    private int screenW;
    private int screenH;
    private Path mPath;
    private Paint mPaint;
    private Bitmap fgBitmap;
    private Canvas mCanvas;
    private Bitmap bgBitmap;
    private float preX;
    private float preY;

    public EraserXfermode(Context context, AttributeSet attrs) {
        super(context, attrs);
        getScreenWH(context);
        init();
    }

    /**
     * 主要是初始化画笔,初始化前景图和背景图
     */
    private void init() {
        mPath = new Path();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);//第二个参数是防抖动的
        mPaint.setARGB(254, 123, 123, 123);

        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setStyle(Paint.Style.STROKE);
        //设置路径结合处的样式
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        //设置笔触类型
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(50);

        fgBitmap = Bitmap.createBitmap(screenW, screenH, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(fgBitmap);
        mCanvas.drawColor(0xFF808080);

        bgBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.screen_protection);
        //bgBitmap 没有手机屏幕那么大,将其缩放至手机屏幕那么大
        bgBitmap = Bitmap.createScaledBitmap(bgBitmap, screenW, screenH, true);
    }

    private void getScreenWH(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        screenW = metrics.widthPixels;
        screenH = metrics.heightPixels;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bgBitmap, 0, 0, null);
        canvas.drawBitmap(fgBitmap, 0, 0, null);
        //??
        mCanvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN://手指触屏充值路径
                mPath.reset();
                mPath.moveTo(x, y);
                preX = x;
                preY = y;
                break;

            case MotionEvent.ACTION_MOVE://手指移动是连接路径
                float dx = Math.abs(x - preX);
                float dy = Math.abs(y - preY);
                if (dx >= MIN_MOVE_DIS || dy >= MIN_MOVE_DIS) {
                    mPath.quadTo(preX, preY, (x + preX) / 2, (y + preY) / 2);
                    preX = x;
                    preY = y;
                }
                break;

        }

        invalidate();
        return true;
    }

}
