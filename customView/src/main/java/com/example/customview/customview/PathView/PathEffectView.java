package com.example.customview.customview.PathView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.SumPathEffect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by to-explore-future on 2017/second/14
 * 这个类 画几条折现
 */
public class PathEffectView extends View {
    private int times;
    private float mPhase;
    private Paint paint;
    private Path path;
    private PathEffect[] mPathEffects;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (times < 30) {

                invalidate();
                times++;
            }

        }
    };


    public PathEffectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        initPaint();
        initPath();
        mPathEffects = new PathEffect[7];
        mPathEffects[0] = null;
        mPathEffects[1] = new CornerPathEffect(20);
        mPathEffects[2] = new DiscretePathEffect(3.0f, 5.0f);
        mPathEffects[3] = new DashPathEffect(new float[]{20, 10, 5, 10}, mPhase);

        //可以自己定义路径的样式
        Path path = new Path();
        path.addRect(0, 0, 8, 8, Path.Direction.CCW);
        mPathEffects[4] = new PathDashPathEffect(path, 12, mPhase, PathDashPathEffect.Style.ROTATE);

        mPathEffects[5] = new ComposePathEffect(mPathEffects[2], mPathEffects[4]);
        mPathEffects[6] = new SumPathEffect(mPathEffects[4], mPathEffects[3]);

    }

    private void initPath() {

    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        path = new Path();
        path.moveTo(0, 0);
        for (int i = 0; i < 30; i++) {
            path.lineTo(i * 30, (float) (Math.random() * 100));
        }

        for (int i = 0; i < mPathEffects.length; i++) {
            paint.setPathEffect(mPathEffects[i]);
            canvas.drawPath(path, paint);
            canvas.translate(0, 250);
        }
        handler.sendEmptyMessageDelayed(0,500);
    }
}
