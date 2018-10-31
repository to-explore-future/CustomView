package com.example.customview.customview.MaskFilterViewPackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.customview.R;
import com.example.customview.utils.GetScreenWH;

/**
 * Created by to-explore-future on 2017/second/14
 */
public class BlurMaskFilterView extends View {

    private Paint alphaPaint;
    private Bitmap bitmap;
    private Bitmap alphaBitmap;
    private Paint paint;
    private int screenH;
    private int screenW;

    public BlurMaskFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        setLayerType(LAYER_TYPE_SOFTWARE, null);

        DisplayMetrics displayMetrics = GetScreenWH.getScreenWH(getContext());
        screenW = displayMetrics.widthPixels;
        screenH = displayMetrics.heightPixels;

        initPaint();
        initBitmap();

    }

    private void initBitmap() {
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.sun);
        alphaBitmap = bitmap.extractAlpha();
    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

        alphaPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        alphaPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.NORMAL));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(screenW, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(screenH, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(alphaBitmap, 200, 400, alphaPaint);

        canvas.drawBitmap(bitmap, 200, 400, paint);

        canvas.drawBitmap(bitmap, 600, 400, paint);
    }
}
