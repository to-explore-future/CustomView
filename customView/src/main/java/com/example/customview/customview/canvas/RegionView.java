package com.example.customview.customview.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Region;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by to-explore-future on 2017/3/25
 */
public class RegionView extends View {

    private float sWidth;
    private float sHeight;
    private Paint paint;
    private Region region1;
    private Region region2;

    private int type;
    private Region.Op[] types ;

    private Handler handler;
    private boolean flag = true;
    private int position;


    public RegionView(Context context, AttributeSet attrs) {
        super(context, attrs);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                invalidate();
            }
        };

        init();
    }

    private void init() {

        types = new Region.Op[]{Region.Op.DIFFERENCE, Region.Op.INTERSECT, Region.Op.REPLACE,
                Region.Op.REVERSE_DIFFERENCE, Region.Op.UNION, Region.Op.XOR};

        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(5);

        region1 = new Region(100, 200, 500, 600);
        region2 = new Region(300, 400, 700, 800);

        new Thread(){
            @Override
            public void run() {

                while(flag){
                    SystemClock.sleep(3000);
                    handler.sendEmptyMessage(0);
                    position++;
                }
            }
        }.start();


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        sWidth = w;
        sHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLUE);

        canvas.save();

        canvas.clipRegion(region1);

        canvas.clipRegion(region2, types[position % types.length]);

        canvas.drawColor(Color.RED);

        canvas.restore();

        /**
         * region 的基准和 rect的基准不一样,region可能是一个屏幕为基准,而rect是一自定义的view为基准,他们的(0,0)位置不同
         */
        canvas.drawRect(100, 138, 500, 538,paint);
        canvas.drawRect(300, 338, 700, 738,paint);

    }
}
