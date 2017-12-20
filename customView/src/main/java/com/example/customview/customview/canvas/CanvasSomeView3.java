package com.example.customview.customview.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by to-explore-future on 2017/3/31
 * saveLayerAlpha 这个方法可以半透明,尝试一下
 */
public class CanvasSomeView3 extends View {

    private final Paint paint;
    private int mViewH;
    private int mViewW;

    public CanvasSomeView3(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mViewH = h;
        mViewW = w;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.RED);
        canvas.drawRect(mViewW / 2 - 200, mViewH / 2 - 200, mViewW / 2 + 200, mViewH / 2 + 200, paint);

        canvas.saveLayerAlpha(mViewW / 2 - 100, mViewH / 2 - 100, mViewW / 2 + 100, mViewH / 2 + 100, 0x55, Canvas.ALL_SAVE_FLAG);
        canvas.rotate(3);

        paint.setColor(Color.BLUE);

        canvas.drawRect(mViewW / 2 - 100, mViewH / 2 - 100, mViewW / 2 + 100, mViewH / 2 + 100,paint);
        canvas.restore();


    }
}
