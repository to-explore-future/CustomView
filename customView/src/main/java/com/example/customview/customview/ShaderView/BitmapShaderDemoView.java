package com.example.customview.customview.ShaderView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.example.customview.R;
import com.example.customview.utils.GetScreenWH;

/**
 * Created by to-explore-future on 2017/second/21
 */
public class BitmapShaderDemoView extends View {

    private Paint paintStrock;
    private int screenHeight;
    private int screenWidth;
    private float x;
    private float y;
    private float radius = 300;
    private Paint fillPaint;

    public BitmapShaderDemoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            x = event.getX();
            y = event.getY();
            invalidate();
        }
        return true;
    }

    private void init() {
        //描一个黑边
        paintStrock = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        paintStrock.setColor(0xFF000000);//纯黑
        paintStrock.setStrokeWidth(5);
        paintStrock.setStyle(Paint.Style.STROKE);

        Bitmap brickBlock = BitmapFactory.decodeResource(getResources(), R.mipmap.brick_block);

        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setShader(new BitmapShader(brickBlock, Shader.TileMode.REPEAT, Shader.TileMode
                .REPEAT));

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
        canvas.drawColor(Color.DKGRAY);

        canvas.drawCircle(x, y, radius, fillPaint);

        canvas.drawCircle(x, y, radius, paintStrock);
    }
}
