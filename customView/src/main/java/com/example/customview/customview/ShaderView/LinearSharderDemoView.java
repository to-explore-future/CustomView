package com.example.customview.customview.ShaderView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.example.customview.R;
import com.example.customview.utils.GetScreenWH;

/**
 * Created by to-explore-future on 2017/second/21
 */
public class LinearSharderDemoView extends View {

    private Paint paint;
    private Bitmap girl;
    private int girlWidth;
    private int girlHeight;
    private int left;
    private int top;
    private int right;
    private int bottom;
    private Rect rect;

    public int screenHeight;
    public int screenWidth;

    public LinearSharderDemoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        DisplayMetrics screenWH = GetScreenWH.getScreenWH(getContext());
        screenHeight = screenWH.heightPixels;
        screenWidth = screenWH.widthPixels;
        girl = BitmapFactory.decodeResource(getResources(), R.mipmap.girl);
        girlWidth = girl.getWidth();
        girlHeight = girl.getHeight();
        Log.i("girl", "girlWidth:" + girlWidth + "--girlHeight:" + girlHeight);
        Log.i("girl", "screenWidth:" + screenWidth + "--screenHeight:" + screenHeight);

        left = 0;
        top = 0;
        right = girlWidth;
        bottom = (int) (girlHeight + girlHeight * 0.2);
        rect = new Rect(0, 0, girlWidth, bottom);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

        paint.setShader(new BitmapShader(girl, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR));

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(screenWidth, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(screenHeight, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawRect(rect, paint);
    }
}
