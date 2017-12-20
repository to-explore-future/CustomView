package com.example.customview.customview.ShaderView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.customview.utils.GetScreenWH;

/**
 * Created by to-explore-future on 2017/second/25
 *
 * 自定义一个view,为了保证view的对各种平台的显示的统一性,里面的各种参数都要以view本身的宽高为基准,
 * 这样这个view不管是到大屏设备还是小屏设备,view中元素们总是保持着同样的比例
 */
public class MultiCircleView extends View {

    private float scale_largeCircleRadiu = 3f / 32f;
    private float scale_smallCircleRadiu = 5f / 64f;
    private float scale_strokeWidth = 1f / 256f;
    private float scale_arcRadiu = 1f / 8f;
    private float scale_text2ArcSpace = 1f / 32;

    private int screenHeight;
    private int screenWidth;
    private Paint paint;
    private float ccx;
    private float ccy;
    private float largeCircleRadiu;
    private float smallCircleRadiu;
    private float strokeWidth;
    private float blankLine;
    private Paint textPaint;
    private Paint arcFillPaint;
    private float arcRadiu;
    private RectF oval;
    private Paint arcStrokePaint;
    private float text2ArcSpace;

    public MultiCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    private void init() {
        getScreenWH();
        initPaint();
    }

    private void initPaint() {
        //初始化描边画笔
        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(Color.WHITE);
        paint.setStrokeCap(Paint.Cap.ROUND);

        //初始化文字居中画笔
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(30);

        //初始化填充弧画笔
        arcFillPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        arcFillPaint.setStyle(Paint.Style.FILL);
        arcFillPaint.setColor(0x55EC6941);

        //初始化描边弧画笔
        arcStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        arcStrokePaint.setStyle(Paint.Style.STROKE);
        arcStrokePaint.setColor(Color.WHITE);
    }

    private void getScreenWH() {
        DisplayMetrics screenWH = GetScreenWH.getScreenWH(getContext());
        screenHeight = screenWH.heightPixels;
        screenWidth = screenWH.widthPixels;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(screenWidth, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(screenHeight, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    //当这个控件或这个这个控件中的子控件 任意一个的尺寸,相对位置发生改变,系统都会重新测量,布局,
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        screenHeight = h;
        screenWidth = w;

        calculation();      //为了保证自定义view的适配性,所有的数据都以view的轮廓为基准,所以一旦view的轮廓改变就得重新计算所有的数据
    }

    //所有的数据都要到这里来初始化,保证所有数据保持与控件的轮廓比例不变
    private void calculation() {
        strokeWidth = scale_strokeWidth * screenWidth;
        ccx = screenWidth / 2;
        ccy = screenHeight / 2;
        largeCircleRadiu = scale_largeCircleRadiu * screenWidth;
        smallCircleRadiu = scale_smallCircleRadiu * screenWidth;
        blankLine = largeCircleRadiu / 10;
        arcRadiu = scale_arcRadiu * screenWidth;
        text2ArcSpace = scale_text2ArcSpace * screenWidth;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制背景
        canvas.drawColor(0xFFF29B76);

        canvas.drawCircle(ccx, ccy, largeCircleRadiu, paint);
        canvas.drawText("中心", ccx, ccy, textPaint);

        canvas.save();
        canvas.rotate(-30, ccx, ccy);
        drawLineCircle(canvas);


        canvas.drawLine(ccx, ccy - largeCircleRadiu * 2 - smallCircleRadiu * 2, ccx, ccy - largeCircleRadiu * 3 - smallCircleRadiu * 2, paint);
        canvas.drawCircle(ccx, ccy - largeCircleRadiu * 3 - smallCircleRadiu * 3, smallCircleRadiu, paint);
        canvas.drawText("画布要旋转", ccx, ccy - largeCircleRadiu * 3 - smallCircleRadiu * 3, textPaint);
        canvas.restore();

        canvas.save();
        canvas.rotate(30, ccx, ccy);
        drawLineCircle(canvas);
        canvas.restore();

        drawBlankLineCircle(canvas,180);
        drawBlankLineCircle(canvas,120);
        drawBlankLineCircle(canvas,-120);

        canvas.save();

        canvas.translate(ccx, ccy);
        canvas.rotate(30);
        canvas.translate(0,- largeCircleRadiu * 2 - smallCircleRadiu);
        canvas.rotate(-30);

        oval = new RectF(-arcRadiu, -arcRadiu, arcRadiu, arcRadiu);
        canvas.drawArc(oval,-150,120,true, arcFillPaint);
        canvas.drawArc(oval,-150,120,false, arcStrokePaint);
        //画布先转到起始的角度,然后每次转动30度
        canvas.rotate(-60);
        for (int i = 0; i < 5; i++) {
            canvas.drawText("文字",0,-arcRadiu - text2ArcSpace,textPaint);
            canvas.rotate(30);
        }
        canvas.restore();

    }

    private void drawBlankLineCircle(Canvas canvas,int canvasDegress) {
        canvas.save();
        canvas.rotate(canvasDegress, ccx, ccy);
        canvas.drawLine(ccx, ccy - blankLine - largeCircleRadiu, ccx, (float) (ccy - blankLine - largeCircleRadiu - largeCircleRadiu * 0.8), paint);
        canvas.drawCircle(ccx, (float) (ccy - blankLine * 2 - largeCircleRadiu * 1.8 - smallCircleRadiu), smallCircleRadiu, paint);
        canvas.drawText("画布要旋转", ccx, (float) (ccy - blankLine * 2 - largeCircleRadiu * 1.8 - smallCircleRadiu), textPaint);
        canvas.restore();
    }

    private void drawLineCircle(Canvas canvas) {
        canvas.drawLine(ccx, ccy - largeCircleRadiu, ccx, ccy - largeCircleRadiu * 2, paint);
        canvas.drawCircle(ccx, ccy - largeCircleRadiu * 2 - smallCircleRadiu, smallCircleRadiu, paint);
        canvas.drawText("右上角", ccx, ccy - largeCircleRadiu * 2 - smallCircleRadiu, textPaint);
    }
}
