package com.example.customview.customview.ShaderView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.customview.R;

public class ViewShadow extends View {

    private Paint shadowPaint;
    private Bitmap srcBitmap;
    private Bitmap alphaChannelBitmap;

    public ViewShadow(Context context) {
        this(context,null);
    }

    public ViewShadow(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setLayerType(LAYER_TYPE_SOFTWARE,null); //关闭硬件加速，因为这个方法不支持硬件加速
        initPaint();
        initResource();
    }

    private void initPaint() {
        shadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        shadowPaint.setColor(Color.GRAY);
        shadowPaint.setMaskFilter(new BlurMaskFilter(40,BlurMaskFilter.Blur.NORMAL));
    }

    private void initResource() {

        srcBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.beautiful);
        alphaChannelBitmap = srcBitmap.extractAlpha();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(alphaChannelBitmap,100,100,shadowPaint);
        canvas.drawBitmap(srcBitmap, 100, 100, null);

    }
}
