package com.example.customview.customview.XformodePackage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 用
 */
public class DrawAlarmClockView extends View {

    private Paint bgRectPaint;
    private Rect bgRect;
    private int bgRectX = 0;
    private int bgRectY = 0;
    private int bgRectHeight = 300;
    private int bgRectWidht = 300;
    private Paint alarmPaint;
    private int alarmCircleR; //白色耳朵的半径
    private int leftAlarmCircleX;
    private int leftAlarmCircleY;
    private int rightAlarmCircleX;
    private int rightAlarmCircleY;
    private int bgRectRight;
    private int bgRectBottom;
    private Paint XferModePaint;
    private int xferModeCircleR;
    private int centerX;
    private int centerY;
    private int arcX;
    private int arcY;
    private int arcRight;
    private int arcBottom;
    private int arcR;
    private RectF arcRectF;
    private Paint arcPaint;

    public DrawAlarmClockView(Context context) {
        this(context, null);
    }

    public DrawAlarmClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();

    }

    private void init() {
        //背景
        bgRectPaint = new Paint();
        bgRectPaint.setColor(Color.parseColor("#F49178"));
        bgRectRight = bgRectX + bgRectWidht;
        bgRectBottom = bgRectY + bgRectHeight;
        bgRect = new Rect(bgRectX, bgRectY, bgRectRight, bgRectBottom);

        //白色的耳朵
        alarmPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        alarmPaint.setColor(Color.WHITE);
        alarmCircleR = bgRectWidht / 5;
        leftAlarmCircleX = bgRectX + bgRectWidht / 4;
        leftAlarmCircleY = bgRectY + bgRectHeight / 4;
        rightAlarmCircleX = bgRectRight - bgRectWidht / 4;
        rightAlarmCircleY = bgRectY + bgRectHeight / 4;

        XferModePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        XferModePaint.setColor(Color.parseColor("#F49178"));
        XferModePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));

        xferModeCircleR = bgRectWidht / 2 - 40;
        centerX = bgRectX + bgRectWidht / 2;
        centerY = bgRectY + bgRectHeight / 2;

        arcR = xferModeCircleR - 20;
        arcX = centerX - arcR;
        arcY = centerY - arcR;
        arcRight = centerX + arcR;
        arcBottom = centerY + arcR;
        arcRectF = new RectF(arcX, arcY, arcRight, arcBottom);
        arcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcPaint.setColor(Color.WHITE);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(20);



    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(bgRect, bgRectPaint);
        int bgRectLayer = canvas.saveLayer(new RectF(bgRect), bgRectPaint);

        canvas.drawCircle(leftAlarmCircleX, leftAlarmCircleY, alarmCircleR, alarmPaint);
        canvas.drawCircle(rightAlarmCircleX, rightAlarmCircleY, alarmCircleR, alarmPaint);

        canvas.drawCircle(centerX, centerY, xferModeCircleR, XferModePaint);
//        alarmPaint.setXfermode(null);

        canvas.drawArc(arcRectF, 0, 360, true, arcPaint);


        canvas.restoreToCount(bgRectLayer);
    }
}
