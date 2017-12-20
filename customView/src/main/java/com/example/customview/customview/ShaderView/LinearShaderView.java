package com.example.customview.customview.ShaderView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.customview.utils.GetScreenWH;

/**
 * Created by to-explore-future on 2017/second/21
 */
public class LinearShaderView extends View {

    public int screenHeight;
    public int screenWidth;

    private Paint paint;

    private Rect rect;
    private int left;
    private int top;
    private int right;
    private int bottom;

    private Rect rect1;
    private int bottom1;
    private int top1;
    private Paint paint2;
    private Paint paint3;

    private int paint3Radius = 15;

    private int top2;
    private int bottom2;
    private Rect rect2;

    private Rect rect3;
    private int top3;
    private int bottom3;

    public LinearShaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        getScreenWH();

        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

        paint3 = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

        paint3.setColor(Color.BLUE);
        paint3.setStyle(Paint.Style.FILL);

        left = screenWidth / 2 - 400;
        top = screenHeight / 2 - 800;
        right = screenWidth / 2 + 400;
        bottom = screenHeight / 2;

        rect = new Rect(left, top, right, bottom);

        top1 = screenHeight / 2 + 100;
        bottom1 = screenHeight / 2 + 900;
        rect1 = new Rect(left, top1, right, bottom1);

        top2 = screenHeight / 2 + 100 + 900;
        bottom2 = screenHeight / 2 + 100 + 900 + 800;
        rect2 = new Rect(left, top2, right, bottom2);


        top3 = screenHeight / 2 + 100 + 900 + 900;
        bottom3 = screenHeight / 2 + 100 + 900 + 900 + 800;
        rect3 = new Rect(left, top3, right, bottom3);

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
        paint.setShader(new LinearGradient(left, top, right, bottom,
                Color.RED, Color.YELLOW, Shader.TileMode.REPEAT));
        canvas.drawRect(rect, paint);
        canvas.drawCircle(left, top, paint3Radius, paint3);
        canvas.drawCircle(right, bottom, paint3Radius, paint3);

        paint.setShader(new LinearGradient(left, top1, left + 400,
                top1 + 400, Color.RED, Color.YELLOW, Shader.TileMode.MIRROR));
        canvas.drawRect(rect1, paint);
        canvas.drawCircle(left, top1, paint3Radius, paint3);
        canvas.drawCircle(left + 400, top1 + 400, paint3Radius, paint3);

        paint.setShader(new LinearGradient(left, top2, right,
                top2, Color.RED, Color.YELLOW, Shader.TileMode.REPEAT));
        canvas.drawRect(rect2, paint);
        canvas.drawCircle(left, top2, paint3Radius, paint3);
        canvas.drawCircle(right, top2, paint3Radius, paint3);

        paint.setShader(new LinearGradient(left, top3, left+400, top3+400,
                new int[]{Color.RED, Color.BLUE, Color.CYAN, Color.GREEN},
        new float[]{0.2f, 0.5f, 0.8f, 0.1f}, Shader.TileMode.REPEAT));
        canvas.drawRect(rect3, paint);
        canvas.drawCircle(left, top3, paint3Radius, paint3);
        canvas.drawCircle(left+400, top3 + 400, paint3Radius, paint3);


    }
}
