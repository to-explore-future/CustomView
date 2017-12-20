package com.example.customview.customview.PathView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.example.customview.R;
import com.example.customview.utils.GetScreenWH;

/**
 * Created by to-explore-future on 2017/first/24
 */
public class PathStartDemo extends View {


    private static final String TAG = "PathStartDemo";
    private Paint paint;
    private Path path;
    private float[] currentPosition;
    private float[] pointX = new float[5];
    private float[] pointY = new float[5];
    private int pathLength;
    private int screenW;
    private int screenH;
    private int mCurrentPath;
    private Bitmap bitmap;
    private PathMeasure pathMeasure;

    public PathStartDemo(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.indicator);

        DisplayMetrics outMetrics = GetScreenWH.getScreenWH(getContext());
        screenW = outMetrics.widthPixels;
        screenH = outMetrics.heightPixels;

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);

        path = new Path();

        currentPosition = new float[2];

        pointX[0] = 500;
        pointY[0] = 0;
        pointX[1] = 800;
        pointY[1] = 900;
        pointX[2] = 100;
        pointY[2] = 300;
        pointX[3] = 900;
        pointY[3] = 300;
        pointX[4] = 200;
        pointY[4] = 900;

        path.moveTo(pointX[0], pointY[0]);
        for (int i = 1; i < 5; i++) {
            path.lineTo(pointX[i], pointY[i]);
        }
        path.lineTo(pointX[0], pointY[0]);

        pathMeasure = new PathMeasure(path, true);
        pathLength = (int) pathMeasure.getLength();
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
        canvas.drawPath(path, paint);
        if (mCurrentPath == 0 && currentPosition[0] == 0 && currentPosition[1] == 0) {
            startAnimator();
        } else {
            canvas.drawBitmap(bitmap, currentPosition[0] - bitmap.getWidth() / 2,
                    currentPosition[1] - bitmap.getHeight() / 2, paint);
        }
    }

    private void startAnimator() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, pathLength);
        valueAnimator.setDuration(10000);
        valueAnimator.setRepeatCount(-1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentPath = (int) animation.getAnimatedValue();
                Log.i(TAG, "mCurrentPath:" + mCurrentPath);
                //这个getPosTan get position tangent获取位置和切线,就是获取组成path的所有的点的坐标
                //然后通过这个点的坐标,去确定bitmap绘制的位置
                pathMeasure.getPosTan(mCurrentPath, currentPosition, null);
                invalidate();
            }
        });
        valueAnimator.start();
    }
}

