package com.example.customview.customview.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.customview.bean.SignsAndPoints;

import java.util.ArrayList;
import java.util.regex.Matcher;

/**
 * Created by to-explore-future on 2017/3/9
 */
public class BrokenLineView extends View {

    private int screenW;
    private int screenH;

    private PointF pointOri;

    private PointF pointYAxisTop;
    private PointF pointXAxisRight;
    private Paint textPaint;
    private int scaleLine;
    private ArrayList<PointF> yAxisPoints;
    private ArrayList<PointF> xAxisPoints;
    private ArrayList<PointF> xAxisTopPoints;
    private ArrayList<PointF> yAxisRightPoints;
    private Paint gridPaint;
    private Paint signsMoneyPaint;
    private Paint signsAgePaint;
    private Path path;
    private Paint pathPaint;
    private Paint axisPaint;
    private ArrayList<PointF> realPoints;
    private ArrayList<SignsAndPoints> yAxisSignsAndPoints;
    private ArrayList<SignsAndPoints> xAxisSignsAndPoints;
    private ArrayList<PointF> simulatePoints;
    private Paint circlePaint;

    public BrokenLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        if (oldw != w || oldh != h) {
            screenW = w;
            screenH = h;
            Log.i("BrokenLineView", "w = " + w + "\t h = " + h);
            init();
        }

    }

    private void init() {

        simulatePoints();

        path = new Path();

        axisPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        axisPaint.setColor(Color.WHITE);
        axisPaint.setStrokeWidth(5);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(60);
        textPaint.setTextAlign(Paint.Align.CENTER);

        gridPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        gridPaint.setColor(Color.WHITE);
        gridPaint.setStrokeWidth(2);

        signsMoneyPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        signsMoneyPaint.setColor(Color.WHITE);
        signsMoneyPaint.setTextSize(40);
        signsMoneyPaint.setTextAlign(Paint.Align.RIGHT);


        signsAgePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        signsAgePaint.setColor(Color.WHITE);
        signsAgePaint.setTextSize(40);
        signsAgePaint.setTextAlign(Paint.Align.CENTER);

        pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        pathPaint.setColor(Color.RED);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setStrokeWidth(5);
        pathPaint.setPathEffect(new CornerPathEffect(15));

        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        circlePaint.setColor(Color.RED);
        circlePaint.setStyle(Paint.Style.FILL);

        initControlPoints();

        initGridFrameCoordinate();

    }

    //simulate some points.
    private void simulatePoints() {
        realPoints = new ArrayList<>();
        realPoints.add(new PointF(10, 0));
        realPoints.add(new PointF(20, 0.5f));
        realPoints.add(new PointF(30, 5));
        realPoints.add(new PointF(40, 50));
        realPoints.add(new PointF(50, 150));
        realPoints.add(new PointF(60, 300));
        realPoints.add(new PointF(70, 500));
        realPoints.add(new PointF(80, 800));
        realPoints.add(new PointF(90, 1200));

        simulatePoints = new ArrayList<>();
    }

    private void initGridFrameCoordinate() {
        scaleLine = screenW / 12;

        initYAxisPoints();
        initXAxisPoints();
        initXAxisTopPoints();
        initYAxisRightPoints();
    }

    /**
     * init the right of the parallel to the points of the y axis.
     */
    private void initYAxisRightPoints() {
        yAxisRightPoints = new ArrayList<>();
        for (int i = 0; i < yAxisPoints.size(); i++) {
            PointF pointF = new PointF(xAxisPoints.get(xAxisPoints.size() - 1).x, yAxisPoints.get(i).y);
            yAxisRightPoints.add(pointF);
        }

    }

    /**
     * init the top of the parallel to the points of the x axis.
     */
    private void initXAxisTopPoints() {
        xAxisTopPoints = new ArrayList<>();
        for (int i = 0; i < xAxisPoints.size(); i++) {
            PointF pointF = new PointF(xAxisPoints.get(i).x, yAxisPoints.get(yAxisPoints.size() - 1).y);
            xAxisTopPoints.add(pointF);
        }

    }

    /**
     * init points on the X axis
     */
    private void initXAxisPoints() {
        xAxisPoints = new ArrayList<>();
        int disX = (int) (pointXAxisRight.x - pointOri.x);
        for (int i = 0; i < disX / scaleLine - 1; i++) {
            PointF pointF = new PointF(pointOri.x + (i + 1) * scaleLine, pointOri.y);
            xAxisPoints.add(pointF);
        }

    }

    /**
     * init points on the Y axis
     */
    private void initYAxisPoints() {
        //need scale line
        yAxisPoints = new ArrayList<>();
        int disY = (int) (pointOri.y - pointYAxisTop.y);
        for (int i = 0; i < disY / scaleLine - 1; i++) {
            PointF pointF = new PointF(pointOri.x, pointOri.y - scaleLine * (i + 1));
            yAxisPoints.add(pointF);
        }
    }

    /**
     * init control points ,include original point,the most right point in the x axis ,the most top point in the y axis .
     */
    private void initControlPoints() {

        float ori_x = screenW * 1 / 12f;
        float ori_y = screenH * 11 / 12f;
        pointOri = new PointF(ori_x, ori_y);

        float xAxis_x = pointOri.x;
        float xAxis_y = screenH * 1 / 12f;
        pointYAxisTop = new PointF(xAxis_x, xAxis_y);

        float yAxis_x = screenW * 11 / 12f;
        float yAxis_y = pointOri.y;
        pointXAxisRight = new PointF(yAxis_x, yAxis_y);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();
                path.moveTo(x, y);

            case MotionEvent.ACTION_MOVE:
                float x1 = event.getX();
                float y1 = event.getY();
                path.lineTo(x1, y1);

        }

        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawColor(Color.parseColor("#9596C4"));
        //draw x axis and y axis ,need original coordinate and two endpoint coordinate.
        drawXYAxis(canvas);

        //draw grid , need grid frame coordinate.
        drawGrid(canvas);

        //draw signs
        drawSigns(canvas);


        canvas.drawText("my money", 540, 100, textPaint);

        canvas.drawText("money", pointYAxisTop.x + 150, pointYAxisTop.y, textPaint);

        canvas.drawText("age", pointXAxisRight.x, pointXAxisRight.y + 100, textPaint);


        initSimulatePoints();

        drawSimulatePoints(canvas);

        canvas.drawPath(path, pathPaint);
    }

    private void initSimulatePoints() {
        //draw the points which get from server.These points is real data,We should transform these points
        //into our recognition of data interface

        Log.i("realPoints.size", "realPoints.size:" + realPoints.size());
        for (int i = 0; i < realPoints.size(); i++) {
            float x = realPoints.get(i).x;
            float y = realPoints.get(i).y;
            Log.i("yyyz", "y:" + y);

            Log.i("run", "time:+++++++++++++++++++++++++++");

            PointF point = new PointF();

            //compare this x with the sign of the points on the x axis. transform to simulate data.
            for (int j = 0; j < xAxisSignsAndPoints.size() - 1; j++) {
                if (x >= xAxisSignsAndPoints.get(j).getSign() && x < xAxisSignsAndPoints.get(j + 1).getSign()) {
                    float scale = x / xAxisSignsAndPoints.get(j).getSign();
                    point.x = xAxisSignsAndPoints.get(j).getPoint().x * scale;
                    Log.i("xy_x", "x:" + point.x);
                    break;
                }
            }
            for (int j = 0; j < yAxisSignsAndPoints.size() - 1; j++) {

                if (y < 10) {
                    float disY = Math.abs(yAxisSignsAndPoints.get(0).getPoint().y - pointOri.y);
                    float scale = y / 10;
                    float realDisY = scale * disY;
                    point.y = pointOri.y - realDisY;

                } else if (y >= yAxisSignsAndPoints.get(j).getSign() && y < yAxisSignsAndPoints.get(j + 1).getSign()) {

                    float lowY = yAxisSignsAndPoints.get(j).getPoint().y;
                    float topY = yAxisSignsAndPoints.get(j + 1).getPoint().y;
                    float disY = Math.abs(lowY - topY);

                    int signLowY = yAxisSignsAndPoints.get(j).getSign();
                    int signTopY = yAxisSignsAndPoints.get(j + 1).getSign();

                    float yShare = Math.abs(signTopY - signLowY);

                    float scale = Math.abs(y - signLowY) / yShare;

                    float realDisY = disY * scale;

                    float yy = yAxisSignsAndPoints.get(j).getPoint().y - realDisY;
                    point.y = yy;

                    break;
                } else if (j == yAxisSignsAndPoints.size() - 1) {

                }
            }

            simulatePoints.add(point);
        }

    }

    private void drawSimulatePoints(Canvas canvas) {
        for (int i = 0; i < simulatePoints.size(); i++) {
            canvas.drawCircle(simulatePoints.get(i).x, simulatePoints.get(i).y, 15, circlePaint);
        }
    }

    private void drawSigns(Canvas canvas) {
        int signY = 10;
        int increment = 10;
        yAxisSignsAndPoints = new ArrayList<>();
        for (int i = 0; i < yAxisPoints.size(); i++) {
            increment += 10;
            canvas.drawText(signY + "", yAxisPoints.get(i).x - 10, yAxisPoints.get(i).y + 10, signsMoneyPaint);
            yAxisSignsAndPoints.add(new SignsAndPoints(signY, yAxisPoints.get(i)));
            signY += increment;

        }
        int age = 10;
        xAxisSignsAndPoints = new ArrayList<>();
        for (int i = 0; i < xAxisPoints.size(); i++) {
            canvas.drawText(age + "", xAxisPoints.get(i).x, xAxisPoints.get(i).y + 35, signsAgePaint);
            xAxisSignsAndPoints.add(new SignsAndPoints(age, xAxisPoints.get(i)));
            age += 10;
        }
    }

    private void drawGrid(Canvas canvas) {
        //draw horizontal line
        for (int i = 0; i < yAxisPoints.size(); i++) {
            canvas.drawLine(yAxisPoints.get(i).x, yAxisPoints.get(i).y, yAxisRightPoints.get(i).x, yAxisRightPoints.get(i).y,
                    gridPaint);
        }

        //draw vertical line
        for (int i = 0; i < xAxisPoints.size(); i++) {
            canvas.drawLine(xAxisPoints.get(i).x, xAxisPoints.get(i).y, xAxisTopPoints.get(i).x, xAxisTopPoints.get(i).y, gridPaint);

        }
    }

    private void drawXYAxis(Canvas canvas) {


        canvas.drawLine(pointOri.x, pointOri.y, pointYAxisTop.x, pointYAxisTop.y, axisPaint);

        canvas.drawLine(pointOri.x, pointOri.y, pointXAxisRight.x, pointXAxisRight.y, axisPaint);


    }
}
