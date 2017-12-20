package com.example.customview.customview.PagerTurn;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by to-explore-future on 2017/4/6
 */
public class PageTurnViewAiGe extends View {

    private Paint textPaint;
    private List<Bitmap> mBitmaps;
    private int mViewW;
    private int mViewH;
    private int start;
    private int end;
    private float clipx;
    private float downX;
    private float downY;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            invalidate();

        }
    };
    private int mMiddle;
    private int pageIndex;
    private boolean isShowed;

    public PageTurnViewAiGe(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mBitmaps = new ArrayList<Bitmap>();

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        textPaint.setTextAlign(Paint.Align.CENTER);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mViewH = h;
        mViewW = w;

        clipx = mViewW;
        mMiddle = mViewW / 2;
    }

    /**
     * Set Bitmaps for this view,if the incoming mBitmaps is null or It's length is smaller then 2,then throw Exception.
     *
     * @param mBitmaps
     */
    public void setBitmaps(List<Bitmap> mBitmaps) {

        /**
         * 如果数据为空则抛出异常
         */
        if (null == mBitmaps || mBitmaps.size() == 0) {
            throw new IllegalArgumentException("no bitmap to display");
        }

        /**
         * 如果数据长度小于2则GG思密达
         */
        if (mBitmaps.size() < 2) {
            throw new IllegalArgumentException("fuck you and fuck to use imageview");
        }

        this.mBitmaps = mBitmaps;

        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                clipx = event.getX();

                isShowed = false;

                invalidate();
                break;

            case MotionEvent.ACTION_MOVE:

                clipx = event.getX();

                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                judgeSlideAuto();
                break;

        }

        return true;
    }

    private void judgeSlideAuto() {

        if (clipx < mMiddle) {
            new Thread() {
                @Override
                public void run() {

                    while (clipx > 0) {
                        clipx -= 0.1;
                        handler.sendEmptyMessage(0);
                    }

                    pageIndex ++;
                    clipx = mViewW;
                }
            }.start();
        }

        if (clipx > mMiddle) {
            new Thread() {
                @Override
                public void run() {

                    while (clipx < mViewW) {
                        clipx += 0.1;
                        handler.sendEmptyMessage(1);
                    }

                }
            }.start();

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        /**
         * If the method SetBitmaps is not invoked
         */
        if (this.mBitmaps.size() == 0) {

            textPaint.setColor(Color.RED);
            textPaint.setTextSize(60);
            canvas.drawText("FBI WARING!", mViewW / 2, mViewH / 4, textPaint);

            textPaint.setTextSize(40);
            textPaint.setColor(Color.BLACK);
            canvas.drawText("please invoke the method setBitmaps()", mViewW / 2, mViewH / 2, textPaint);
            return;
        }


        start = mBitmaps.size() - 2 - pageIndex;
        end = mBitmaps.size() - pageIndex;



        if (start <= -1) {
            start = 0;
            end = 1;
            canvas.drawBitmap(mBitmaps.get(0), 0, 0, null);
            if (!isShowed) {        //
                showToast("this is the last one!");
                isShowed = true;
            }
            return;
        }

        /**
         * Draw the bitmaps in the mBitmaps.In order to save memory there only draw two bitmaps
         */
        for (int i = start; i < end; i++) {

            canvas.save();

            if (i == end - 1) {
                canvas.clipRect(0, 0, clipx, mViewH);
            }
            canvas.drawBitmap(mBitmaps.get(i), 0, 0, null);

            canvas.restore();

        }
    }

    private void showToast(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }
}
