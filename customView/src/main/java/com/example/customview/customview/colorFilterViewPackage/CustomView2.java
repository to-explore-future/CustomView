package com.example.customview.customview.colorFilterViewPackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.AttributeSet;
import android.view.View;

import com.example.customview.R;

/**
 * Created by to-explore-future on 2017/first/19 11:48
 */
public class CustomView2 extends View {

    private Paint paint;
    private Bitmap bitmap;

    public CustomView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    private void initData() {
        initPaint();
        initRes();

    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        ColorFilter porterDuffColorFilter = new PorterDuffColorFilter(Color.RED, PorterDuff.Mode
                .DARKEN);
        paint.setColorFilter(porterDuffColorFilter);

    }

    private void initRes() {
        bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.color_filer_test);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(bitmap.getWidth(), MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(bitmap.getHeight(), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawBitmap(bitmap, 20f, 0f, paint);
    }


}
