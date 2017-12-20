package com.example.customview.customview.PathView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by to-explore-future on 2017/3/second
 */
public class WaveView extends View {

    private Path path;
    private Paint paint;

    private int vWidth;
    private int vHeight;
    private float waveY;

    //control point
    private float ctrX;
    private float ctrY;

    //water state :true is water level grow , false is water level shrink,
    private boolean growHeight;
    private boolean toRight;

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }


    private void init() {
        path = new Path();

        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        paint.setColor(0xFFA2D6AE);


    }

    //whe the view size is changed ,this method will be invoke.
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.i("param", "w:" + w + "---h:" + h + "---oldw:" + oldw + "---oldh:" + oldh);

        vWidth = w;
        vHeight = h;

        waveY = 1 / 8f * vHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //设置一个起点
        path.moveTo(-1 / 4f * vWidth, waveY);

        path.quadTo(ctrX, ctrY, 5 / 4f * vWidth, waveY);

        path.lineTo(5 / 4f * vWidth, vHeight);

        path.lineTo(-1 / 4f * vWidth, vHeight);

        path.close();

        canvas.drawPath(path, paint);

        /**
         * control water height
         */
        if (waveY >= vHeight) {
            growHeight = true;
        } else if (waveY <= 1 / 8f * vHeight) {
            growHeight = false;
        }

        if (growHeight) {       //water level grow;
            waveY -= 5;
            ctrY -= 5;
        } else {
            waveY += 5;
            ctrY += 5;

        }

        /**
         * control water wave
         */
        if (ctrX <= 0) {
            toRight = true;
        }
        if (ctrX >= vWidth) {
            toRight = false;
        }

        if (toRight) {
            ctrX += 25;
        } else {
            ctrX -= 25;
        }
        path.reset();
        SystemClock.sleep(10);
        invalidate();
        Log.i("run1", "waveY" + waveY);

    }
}
