package com.example.customview.customview.MaskFilterViewPackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.example.customview.R;
import com.example.customview.utils.GetScreenWH;

/**
 * Created by to-explore-future on 2017/second/5
 * 画一个方块,使用MaskFilter的子类,让方框显示出阴影
 */
public class MaskFilterView extends View {

    private Paint paint;
    private int screenW;
    private int screenH;
    private RectF rectF;
    private Handler handler;
    private Paint textPaint;
    private String[] texts;
    private int position;
    private Bitmap beautifulGirlBitmap;
    private Rect rect;
    private Bitmap scaledBitmap;

    public MaskFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                invalidate();
            }
        };

        final BlurMaskFilter.Blur blurs[] = new BlurMaskFilter.Blur[]{BlurMaskFilter.Blur.INNER,
                BlurMaskFilter.Blur.NORMAL, BlurMaskFilter.Blur.OUTER, BlurMaskFilter.Blur.SOLID,};
        texts = new String[]{"inner", "normal", "outer", "solid"};

        //这个类关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        DisplayMetrics displayMetrics = GetScreenWH.getScreenWH(getContext());
        screenW = displayMetrics.widthPixels;
        screenH = displayMetrics.heightPixels;

        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        //设置模糊滤镜,实现所绘制物体的模糊效果
        paint.setMaskFilter(new BlurMaskFilter(200f, BlurMaskFilter.Blur.INNER));

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        textPaint.setColor(Color.BLUE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(80);

        rectF = new RectF(200, 600, 800, 1400);
        rect = new Rect(200, 600, 800, 1400);

        new Thread() {
            @Override
            public void run() {
                for (int i = 1; i < 1000; i++) {
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    paint.setMaskFilter(new BlurMaskFilter(200f, blurs[i % 4]));
                    position++;
                    Log.i("position", "i:" + i + "===position:" + position);

                    handler.sendEmptyMessage(0);

                }
            }
        }.start();

        beautifulGirlBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.beautiful);
        scaledBitmap = Bitmap.createScaledBitmap(beautifulGirlBitmap, 600, 800, true);


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
//        canvas.drawRect(rectF, paint);
        canvas.drawBitmap(scaledBitmap,100,100,paint);
        canvas.drawText(texts[position % 4], canvas.getWidth() / 2, screenH / 2, textPaint);
    }
}
