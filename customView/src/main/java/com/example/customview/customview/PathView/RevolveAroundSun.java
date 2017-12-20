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
import android.view.animation.LinearInterpolator;

import com.example.customview.R;
import com.example.customview.utils.GetScreenWH;

/**
 * Created by to-explore-future on 2017/first/25
 */
public class RevolveAroundSun extends View {

    private static final String TAG = "RevolveAroundSun";
    private Paint paint;
    private Path path;
    private int screenW;
    private int screenH;
    private Bitmap sun;
    private Bitmap earth;
    private int[] sunPosition;
    private PathMeasure pathMeasure;
    private int length;
    private float[] earthPosition;
    private int mCurrentPath;

    public RevolveAroundSun(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        DisplayMetrics outMetrics = GetScreenWH.getScreenWH(getContext());
        screenW = outMetrics.widthPixels;
        screenH = outMetrics.heightPixels;

        sun = BitmapFactory.decodeResource(getResources(), R.mipmap.sun);
        earth = BitmapFactory.decodeResource(getResources(), R.mipmap.earth);

        sunPosition = new int[2];
        sunPosition[0] = 500 - sun.getWidth() / 2;
        sunPosition[1] = 1000 - sun.getHeight() / 2;

        earthPosition = new float[2];


        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#80808080"));
        paint.setStyle(Paint.Style.STROKE);

        path = new Path();
        path.addCircle(500, 1000, 400, Path.Direction.CW);

        pathMeasure = new PathMeasure(path, true);
        length = (int) pathMeasure.getLength();
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
        canvas.drawBitmap(sun, sunPosition[0], sunPosition[1], paint);
        if (earthPosition[0] == 0 && earthPosition[1] == 0 && mCurrentPath == 0) {
            startAnimator();
        } else {
            canvas.drawBitmap(earth, earthPosition[0] - earth.getWidth() / 2, earthPosition[1] - earth
                    .getHeight() / 2, paint);
        }
    }

    private void startAnimator() {



        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, length);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(5000);
        valueAnimator.setRepeatCount(-1);
        //
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentPath = (int) animation.getAnimatedValue();
                Log.i(TAG, "mCurrentPath:" + mCurrentPath);
                pathMeasure.getPosTan(mCurrentPath, earthPosition, null);
                invalidate();
            }
        });
        valueAnimator.start();
    }
}
