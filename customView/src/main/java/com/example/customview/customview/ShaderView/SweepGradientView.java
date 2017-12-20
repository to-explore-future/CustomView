package com.example.customview.customview.ShaderView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.customview.utils.GetScreenWH;

/**
 * Created by to-explore-future on 2017/second/22
 */
public class SweepGradientView extends View {

    private Paint paint;
    private Rect rect;
    private Paint paint1;
    private Rect rect2;
    private Paint paint2;

    public SweepGradientView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public int screenHeight;
    public int screenWidth;

    private void init(){
        getScreenWH();

        paint = new Paint();
        paint.setShader(new SweepGradient(400, 400, Color.RED, Color.YELLOW));

        paint1 = new Paint();
        paint1.setColor(Color.GREEN);
        paint1.setStyle(Paint.Style.FILL);

        rect = new Rect(0, 0, 800, 800);

        rect2 = new Rect(0,900,800,1700);
        paint2 = new Paint();
        paint2.setShader(new SweepGradient(400, 1300,
                new int[]{Color.RED, Color.GREEN, Color.YELLOW}, null));
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
        canvas.drawRect(rect, paint);

        canvas.drawCircle(400, 400, 20, paint1);

        canvas.drawRect(rect2, paint2);


    }
}
