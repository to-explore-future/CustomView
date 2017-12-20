package com.example.customview.customview.XformodePackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.Log;
import android.view.View;

import com.example.customview.R;

/**
 * Created by to-explore-future on 2017/first/22 16:20
 * 这个练习PorterDuffXfermode的实际应用,两个美女图
 */
public class PorterDuffXfermodeExample extends View {


    private Bitmap dstGirl;
    private Bitmap srcGirl;
    private Paint paint;
    private PorterDuff.Mode mode;
    private PorterDuffXfermode porterDuffXfermode;
    private int height;
    private int width;

    public PorterDuffXfermodeExample(Context context, PorterDuff.Mode mode) {
        super(context);

        this.mode = mode;
        initData();
    }

    private void initData() {
        initPorterDuffXfermode();
        initBitmap();
        initPaint();
    }

    private void initPorterDuffXfermode() {
        porterDuffXfermode = new PorterDuffXfermode(mode);
    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    private void initBitmap() {
        dstGirl = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.dst_girl);
        srcGirl = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.src_girl);
        height = dstGirl.getHeight();
        width = dstGirl.getWidth();
        Log.i("Example", "height:" + height + "==width:" + width);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.i("Example", "没走这个方法");
        if (porterDuffXfermode == null || mode == null) {
            Log.i("Example", "走空了吗");
        } else {
            canvas.drawBitmap(dstGirl, 0f, 0f, paint);

            paint.setXfermode(porterDuffXfermode);

            canvas.drawBitmap(srcGirl, 0f, 0f, paint);
            Log.i("Example", "正常画了");
            paint.setXfermode(null);
        }
    }
}
