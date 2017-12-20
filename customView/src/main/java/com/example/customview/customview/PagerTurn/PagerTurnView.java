package com.example.customview.customview.PagerTurn;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.customview.utils.GetScreenWH;

import java.util.ArrayList;

public class PagerTurnView extends View {

    private static final int ACTION_MOVE = 2;
    private static final int ACTION_UP = 3;
    private static final int ACTION_NULL = 1;
    private int mScreenW;
    private int mScreenH;
    private ArrayList<Bitmap> mBitmaps;
    private int start;
    private int end;
    private float startX;
    private float startY;
    private float pointX;
    private float pointY;
    private float transX;
    private float transY;
    private int motionEvent;
    private float upX;
    private float upY;
    private Path path;
    private float a;
    private float b;
    private float valueX;
    private float valueY;
    private boolean stateVertical;
    private Paint textPaint;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            invalidate();
            Log.i("handler", "主线程更改UI");
        }
    };
    private int pageIndex;
    private int actionState = ACTION_NULL;
    private boolean lastPage;
    private boolean disPoseTag = true;
    private boolean showLast = true;
    private boolean hasShowed = false;


    public PagerTurnView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);


        DisplayMetrics screenWH = GetScreenWH.getScreenWH(getContext());
        mScreenW = screenWH.widthPixels;
        mScreenH = screenWH.heightPixels;
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(60);
        textPaint.setTextAlign(Paint.Align.CENTER);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(mScreenW, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(mScreenH, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mScreenW = w;
        mScreenH = h;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                startX = event.getX();
                startY = event.getY();

                path = new Path();
                path.moveTo(startX, startY);

                break;
            case MotionEvent.ACTION_MOVE:

                pointX = event.getX();
                pointY = event.getY();

                transX = pointX - startX;
                transY = pointY - startY;

                motionEvent = ACTION_MOVE;

                if (!lastPage) {
                    invalidate();
                }

                break;
            case MotionEvent.ACTION_UP:
                upX = event.getX();
                upY = event.getY();
                /**
                 * 根据两个点可以算出一条 直线方程(两点确定一条直线)
                 * y = ax + b ,任何一条直线一定是这种关系,所以只需要根据两个点求出a,b.
                 */
                //y的差值除以x的差值,就是单位x对应的y的值
                a = (upY - startY) / (upX - startX);
                //b = y - ax ;
                b = upY - a * upX;
                /**
                 * 得到了方程 就下来就是通过用户的手势判断方向,当用户抬手是,继续不断地移动图片知道图片移除屏幕
                 */

                path.lineTo(upX, upY);

                judgeOrientation();
                break;
        }

        return true;
    }

    /**
     * 判断手势的方向,通过x 来判断,只有三种可能,
     * 1.x变大-->往右,
     * 2.x变小-->往左,
     * 3.x不变-->垂直的-->又分为两种情况
     * y变大-->垂直往上
     * y变小-->垂直往下
     */
    private void judgeOrientation() {

        if (lastPage) {
            Toast.makeText(getContext(), "这个是最后一张1", Toast.LENGTH_SHORT).show();
            return;
        }
        /**
         * 用来判断方向
         */
        valueX = upX - startX;
        valueY = startY - upY;

        if (valueX > 0) {
            stateVertical = false;
            //往右滑动 -->让x不断地增加,从而算出x对应的y的值,得出一个点,
            new Thread() {
                @Override
                public void run() {

                    while (pointX - startX < mScreenW) {        //只要还没有画出屏幕,就一直改变transX的值

                        pointX += 0.2;
                        pointY = a * pointX + b;
                        handler.sendEmptyMessage(0);
                    }

                    initValue();
                    if (!lastPage) {
                        pageIndex++;
                    }
                    handler.sendEmptyMessage(1);
                }
            }.start();

        } else if (valueX < 0) {
            stateVertical = false;
            //往左滑动
            new Thread() {
                @Override
                public void run() {
                    while (startX - pointX < mScreenW) {

                        pointX -= 0.2;
                        pointY = a * pointX + b;
                        handler.sendEmptyMessage(0);
                    }

                    initValue();
                    if (!lastPage) {
                        pageIndex++;
                    }
                    handler.sendEmptyMessage(1);
                }
            }.start();

        } else {
            //垂直滑动
            if (valueY > 0) {
                //往上滑动
                /**
                 * 正常情况下,一条斜线,可以使用方程表示,但是这种竖直的线中所有的点的x都是0,只是y在不断地变化,
                 * 所以遇到这种情况,拿到pointY,不断改变就可以
                 */
                new Thread() {
                    @Override
                    public void run() {
                        while (startY - pointY < mScreenH) {
                            pointY -= 0.1;
                            stateVertical = true;
                            handler.sendEmptyMessage(1);
                        }

                        initValue();
                        if (!lastPage) {
                            pageIndex++;
                        }
                    }
                }.start();

            } else {
                //往下滑动
                new Thread() {
                    @Override
                    public void run() {
                        while (pointY - startY < mScreenH) {
                            pointY += 0.1;
                            stateVertical = true;
                            handler.sendEmptyMessage(1);
                        }

                        /**
                         * 当上一个图片消失之后,重置所有的值,
                         */
                        initValue();

                        if (!lastPage) {
                            pageIndex++;
                        }
                    }
                }.start();

            }
        }
    }

    private void initValue() {
        startX = 0;
        startY = 0;
        pointX = 0;
        pointY = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        start = mBitmaps.size() - 2 - pageIndex;
        end = mBitmaps.size() - pageIndex;
        Log.i("startend", "start:" + start + "-----------end:" + end);

        if (start <= -1) {
            lastPage = true;
            if (lastPage) {
                if (!hasShowed) {
                    hasShowed = true;
                    Toast.makeText(getContext(), "这个是最后一张2", Toast.LENGTH_SHORT).show();
                }
            }
            canvas.drawBitmap(mBitmaps.get(0), 0, 0, null);
            return;
        }

        transX = pointX - startX;
        transY = pointY - startY;

        for (int i = start; i < end; i++) {

            if (i == end - 1) {                                                 //最后一张图,显示在屏幕上
                canvas.drawBitmap(mBitmaps.get(i), transX, transY, null);       //最后一张都要移动,改变一下绘制的起点就可以
            } else if (i < end - 1) {                                           //倒数第二张图,在下面
                canvas.drawBitmap(mBitmaps.get(i), 0, 0, null);
            }
        }
    }

    public void setmBitmaps(ArrayList<Bitmap> bitmaps) {
        this.mBitmaps = initBitmaps(bitmaps);
        invalidate();
    }

    private ArrayList<Bitmap> initBitmaps(ArrayList<Bitmap> bitmaps) {
        ArrayList<Bitmap> initBitmaps = new ArrayList<>();
        for (int i = 0; i < bitmaps.size(); i++) {
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmaps.get(i), mScreenW, mScreenH, true);
            initBitmaps.add(scaledBitmap);
        }
        return initBitmaps;
    }
}