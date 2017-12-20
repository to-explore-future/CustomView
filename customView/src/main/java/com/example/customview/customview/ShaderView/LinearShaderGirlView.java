package com.example.customview.customview.ShaderView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.customview.R;
import com.example.customview.utils.GetScreenWH;

/**
 * Created by to-explore-future on 2017/second/22
 */
public class LinearShaderGirlView extends View {

    private Bitmap girl;
    private Bitmap invertedImage;
    private Paint paint;
    private int startX;
    private int startY;
    private Rect rect;
    private PorterDuffXfermode mXfermode;
    private Rect rect1;
    private Paint paint1;

    public LinearShaderGirlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public int screenHeight;
    public int screenWidth;

    private void init() {

        getScreenWH();
        //得到这个图片
        girl = BitmapFactory.decodeResource(getResources(), R.mipmap.girl);

        //通过测试得知 这个是得到一张反转的图片
        Matrix matrix = new Matrix();
        matrix.setScale(1, -1);
        invertedImage = Bitmap.createBitmap(girl, 0, 0, girl.getWidth(), girl.getHeight(),
                matrix, true);

        //起点
        startX = screenWidth / 2 - girl.getWidth() / 2;
        startY = screenHeight / 2 - girl.getHeight() / 2;

        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        paint.setShader(new LinearGradient(0, girl.getHeight(), 0,
                girl.getHeight() + girl.getHeight() / 4, 0xAA000000, Color.TRANSPARENT, Shader.TileMode.CLAMP));

        rect = new Rect(0, girl.getHeight(), girl.getWidth(), girl.getHeight() + girl.getHeight());

        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);


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

        canvas.drawBitmap(girl, 0, 0, paint);

        /*我估计是把当前的图层保存和这里面的宽和高没有什么关系*/
        int sc = canvas.saveLayer(0, 0, girl.getWidth(), girl.getHeight() * 2, null,
                Canvas.ALL_SAVE_FLAG);

        canvas.drawBitmap(invertedImage, 0, girl.getHeight(), null);

        paint.setXfermode(mXfermode);
//
        canvas.drawRect(rect, paint);
//
        paint.setXfermode(null);

        canvas.restoreToCount(sc);


    }
}
