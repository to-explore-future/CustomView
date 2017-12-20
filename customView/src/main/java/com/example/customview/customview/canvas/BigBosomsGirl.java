package com.example.customview.customview.canvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.customview.R;

/**
 * Created by to-explore-future on 2017/second/26
 * 通过对BitmapMesh的学习 搞一个触摸大波妹的效果
 */
public class BigBosomsGirl extends View {

    private Paint origPaint;
    private Paint movePaint;
    private Paint linePaint;

    private Bitmap bitmap;

    //根据点的个数,就知道这个图片被分成了9*9的网格
    private int countX = 8;                    //x轴上的点的个数
    private int countY = 8;
    private float[] origianl;
    private float[] moved;

    private Paint paint;
    private float clickX;
    private float clickY;

    private float standerValue;
    private float[] saveMoved;


    public BigBosomsGirl(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);                     //触摸的时候是否获取到焦点

        init();


    }

    private void init() {


        // 实例画笔并设置颜色
        origPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        origPaint.setColor(0x660000FF);                 //半透明的蓝色
        movePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        movePaint.setColor(0x99FF0000);                 //半透明的红色
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(0xFFFFFB00);                 //

        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        paint.setColor(Color.YELLOW);


        //初始化图片
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.big_bosoms_gril);

        //所有的点是否移动 有一个标准值,值的大小会影响图片扭曲的程度
        standerValue = bitmap.getWidth() / (countX - 1);

        //初始化两个网格点的数组,用于画点
        origianl = new float[countX * countY * 2];
        moved = new float[countX * countY * 2];
        saveMoved = new float[countX * countY * 2];
        initCc();


    }

    private void initCc() {

        //初始化坐标数组
        int index = 0;
        for (int y = 0; y < countY; y++) {

            float fy = bitmap.getHeight() * y / countY;

            for (int x = 0; x < countX; x++) {

                float fx = bitmap.getWidth() * x / countX;

                setXY(origianl, index, fx, fy);
                setXY(moved, index, fx, fy);
                setXY(saveMoved, index, fx, fy);

                index += 1;
                Log.i("index", "index" + index);
            }

        }


    }

    private void setXY(float array[], int index, float fx, float fy) {
        array[index * 2] = fx;
        array[index * 2 + 1] = fy;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawBitmapMesh(bitmap, countX - 1, countY - 1, moved, 0, null, 0, null);
        //绘制big bosoms girl

        for (int i = 0; i < countX * countY; i++) {         //绘制网格点

            canvas.drawCircle(origianl[i * 2], origianl[i * 2 + 1], 5, origPaint);

            canvas.drawCircle(moved[i * 2], moved[i * 2 + 1], 5, movePaint);

            canvas.drawLine(origianl[i * 2], origianl[i * 2 + 1], moved[i * 2], moved[i * 2 + 1], origPaint);
        }

        canvas.drawCircle(clickX, clickY, 15, paint);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        for (int i = 0; i < moved.length; i++) {
            moved[i] = saveMoved[i];
        }

        clickX = event.getX();
        clickY = event.getY();
        calCoordinate();
        invalidate();


        return true;
    }

    //当手指移动的时候,所有的点的坐标都要跟着手指的点击点 发生改变,距离越近的改变的越大,距离越远的改变的越小
    private void calCoordinate() {

        for (int i = 0; i < countX * countY; i++) {
            //一个点一个点的搞
            float fx = moved[i * 2];
            float fy = moved[i * 2 + 1];

            float absX = Math.abs(fx - clickX);
            float absY = Math.abs(fy - clickY);
            double originalDistance = Math.sqrt(absX * absX + absY * absY);
            Log.i("ori", "ori:" + originalDistance);
            double shouldMovedDistance = getNewPointMovedDidstance(originalDistance);  //这个点应当移动的距离
            Log.i("dis", "dis:" + shouldMovedDistance);

            //得到移动的距离移动,把要移动的点的坐标移动到新的点的坐标
            //通过画图 根据等比计算,得出新的点的x 和 y的值

            float distanceX = (float) (shouldMovedDistance * Math.abs(clickX - fx) /
                    originalDistance / 2);
            float distanceY = (float) (shouldMovedDistance * Math.abs(clickY -fy) /
                    originalDistance / 2);
            Log.i("point", "pointx:" + distanceX + "---pointY:" + distanceY);

            //
            getNewPointCoordinate(fx, fy, distanceX, distanceY, i);


        }
    }

    private void getNewPointCoordinate(float fx, float fy, float distanceX, float distanceY, int i) {
        float x = 0;
        float y = 0;
        if (fy <= clickY) {          //如果for循环的点的坐标在点击点的坐标的上面

            if (fx < clickX) {      //上面,左面
                x = fx + distanceX;
            } else if (fx > clickX){                //上面,右面
                x = fx - distanceX;

            }else {                     //fx == clickX
                x = fx;
            }
            y = fy + distanceY;

        } else {                    //下面
            if (fx < clickX) {      //下面,左面
                x = fx + distanceX;

            } else if (fx >clickX){                //下面,右面
                x = fx - distanceX;
            }else{                  //fx ==clickX
                x = fx ;
            }
            y = fy - distanceY;
        }

        moved[i * 2] = x;
        moved[i * 2 + 1] = y;
    }

    private double getNewPointMovedDidstance(double originalDistance) {
        //标准距离小于原始距离,按照比例移动
        if (standerValue < originalDistance) {
            return standerValue * standerValue / originalDistance ;
        } else {          //如果这个点 到点击点的距离小于标准距离,直接返回这个点到点击点的距离即可(让那个点移动到点击点)
            return originalDistance;
        }
    }
}
