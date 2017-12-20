package com.example.customview.customview.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by to-explore-future on 2017/second/27
 */
public class CanvasClipView extends View {
    public CanvasClipView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.RED);
        canvas.clipRect(0, 0, 500, 500);
        canvas.drawColor(Color.BLUE);

    }
}
