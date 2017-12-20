package com.example.customview.customview.PagerTurn;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.customview.utils.LineUtil;

/**
 * Created by to-explore-future on 2017/4/7
 */
public class PagerTurnViewBrokenLineView extends View {

    private static final int PAGE_AUTO_TURN_BACK_SLOWLY = 1;
    private final Paint pathPaint;
    private int mViewH;
    private int mViewW;
    private float a;
    private float b;
    private Path path;
    private float k;
    private float l;

    private float pageAutoBackEachDisAmount = 100; //This control the time of the page auto turn back

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == PAGE_AUTO_TURN_BACK_SLOWLY) {
                invalidate();
            }
        }
    };
    private long autoTurnBackTime;
    private long eachTime;
    private boolean isDraw;
    private float downX;
    private float downY;
    private float eachX;
    private float eachY;
    private boolean end;

    public PagerTurnViewBrokenLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        pathPaint.setColor(Color.GRAY);
        pathPaint.setStrokeWidth(4);
        pathPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mViewH = h;
        mViewW = w;

        path = new Path();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        isDraw = true;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                end = false;

                a = event.getX();
                b = event.getY();

                amendmentPoint();

                downX = a;
                downY = b;

                invalidate();

                break;

            case MotionEvent.ACTION_MOVE:

                a = event.getX();
                b = event.getY();
                /**
                 * If the point in this rang which center point is (0,mViewH),radius is mViewW.
                 */


                amendmentPoint();

                if (Math.abs(a - downX) > 10 && Math.abs(b - downY) > 10) {

                    invalidate();
                }

                break;

            case MotionEvent.ACTION_UP:
                a = event.getX();
                b = event.getY();

                amendmentPoint();

                end = true;
                //Make this page turn back slowly.
                if (a > mViewW / 2) {
                    float disX = mViewW - a;
                    float disY = mViewH - b;

                    eachX = disX / pageAutoBackEachDisAmount;
                    eachY = disY / pageAutoBackEachDisAmount;

                    autoTurnBackTime = 500;
                    eachTime = (long) (autoTurnBackTime / pageAutoBackEachDisAmount);
                    new Thread() {
                        @Override
                        public void run() {
                            while (a < mViewW || b < mViewH) {
                                a += eachX;
                                b += eachY;
                                SystemClock.sleep(eachTime);
                                handler.sendEmptyMessage(PAGE_AUTO_TURN_BACK_SLOWLY);
                            }
                        }
                    }.start();

                } else {

                    float disX = Math.abs(a - mViewW);
                    float disY = Math.abs(mViewH - b);

                    eachX = disX / pageAutoBackEachDisAmount;
                    eachY = disY / pageAutoBackEachDisAmount;

                    autoTurnBackTime = 1000;
                    eachTime = (long) (autoTurnBackTime / pageAutoBackEachDisAmount);
                    new Thread() {
                        @Override
                        public void run() {
                            while (a > -mViewW || b < mViewH) {
                                a -= eachX;
                                b += eachY;
                                SystemClock.sleep(eachTime);
                                handler.sendEmptyMessage(PAGE_AUTO_TURN_BACK_SLOWLY);
                            }
                        }
                    }.start();

                }

                break;
        }
        return true;
    }

    private void amendmentPoint() {
        if (Math.pow(a, 2) + Math.pow(mViewH - b, 2) > Math.pow(mViewW, 2)) {

            float h = mViewH - b;


            float r = (float) Math.sqrt(Math.pow(a, 2) + Math.pow(h, 2));

            float x = (mViewW * a) / r;
            float y = (mViewW * h) / r;

            a = x;
            b = mViewH - y;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isDraw) {

            path.moveTo(a, b);

            k = mViewW - a;
            l = mViewH - b;

            float temp = (float) (Math.pow(k, 2) + Math.pow(l, 2));
            //连接到右侧屏幕边上的点,
            float pointT_Y = mViewH - temp / (2 * l);
            float pointB_x = mViewW - temp / (2 * k);
            PointF A = new PointF(mViewW, pointT_Y);
            PointF B = new PointF(pointB_x, mViewH);
            PointF T = new PointF(mViewW, pointT_Y);
            PointF P = new PointF(a,b);


            //如果这个t点还在屏幕上
            if (pointT_Y > 0) {
                path.lineTo(mViewW, pointT_Y);
            } else {        //如果这t点跑到屏幕上面去了,就需要就算A点
                //P点和A点可以连接一条直线
                //如果一条直线的方程是 y = mx + n;
                //只需要求出a 和 b 那么这条直线就求出来了
                float[] lineParamPT = LineUtil.getLineParam(P, T);

                float m = lineParamPT[0];
                float n = lineParamPT[1];

                float[] lineParamBT = LineUtil.getLineParam(B,T);

                float r = lineParamBT[0];
                float s = lineParamBT[1];

                //所以这条线就拿到了,当y = 0 的时候,就能求出A点的坐标
                //y = mx + n, --> 0 = mx + n; --> -n = mx --> x = -n / m;
                //所以A点的坐标 A( -n / m ,0)
                path.lineTo(-n / m, 0);//连接到A点
                path.lineTo(-s / r, 0);//连接到C点

            }

            path.lineTo(pointB_x, mViewH);

            path.close();

            canvas.drawPath(path, pathPaint);

            path.reset();
        }

    }

}
