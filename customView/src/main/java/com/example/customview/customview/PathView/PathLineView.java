package com.example.customview.customview.PathView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by to-explore-future on 2017/3/3
 */
public class PathLineView extends View {

    private int sendMs = 10;

    private long startTime;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 10) {
                invalidate();
            }
        }
    };

    private ArrayList<ArrayList<Point>> pointLists = new ArrayList<>();//the pointLists contains pointList, pointList contains pointLists.get(0);

    private ArrayList<Paint> paints = new ArrayList<>();

    private ArrayList<Path> paths = new ArrayList<>();


    private int[] colors = {Color.GREEN,Color.RED,Color.BLACK,Color.CYAN,Color.GRAY,Color.BLUE,Color.MAGENTA};






    public PathLineView(Context context, AttributeSet attrs) {
        super(context, attrs);

        pointLists.add(new ArrayList<Point>()); // the first pointList in the pointLists is used to contain the original pointLists.get(0) which user add to this view

    }

    public void addPoint(Point point) {
        pointLists.get(0).add(point);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw || h != oldh) {
            init();
        }
    }

    private void init() {

        initPoint();

        initPaint();

        initPath();

        startTime = System.currentTimeMillis();
    }

    /**
     * User add some points , then this points generate another some points , then anther some points generate anther some
     * points again! Agree to continue ,until generate only two points.
     * Every time generate a set of points,add these points to a arrayList.
     */
    private void initPoint() {
        //if the original points number is 4 , these 4 points generates a path of first layer,
        //one point next to anther point can generate on point,so the 4 points of first layer can generate three points.
        //then three points genetate two points.
        for (int i = 0; i < pointLists.get(i).size() - 2; i++) {

            ArrayList<Point> points = new ArrayList<>();
            pointLists.add(points);

            for (int j = 0; j < pointLists.get(i).size() - 1; j++) {
                pointLists.get(i + 1).add(pointLists.get(i).get(j));

            }
        }

        for (int i = 0; i < pointLists.size(); i++) {
            for (int j = 0; j < pointLists.get(i).size(); j++) {
                System.out.print("ccc:" + pointLists.get(i).get(j).toString());
            }
        }

    }

    private void initPaint() {
        for (int i = 0; i < pointLists.get(0).size() - 1; i++) {
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
            paint.setColor(colors[i]);
            paint.setStrokeWidth(5);
            paint.setStyle(Paint.Style.STROKE);

            paints.add(paint);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        float timePercentage = getTimePercentage();

        changePoints(timePercentage, canvas);

        drawLine(canvas);

        if (timePercentage >= 1) {
            handler.removeCallbacksAndMessages(null);
        } else {
            SystemClock.sleep(50);

            handler.sendEmptyMessage(sendMs);
        }
    }

    private float getTimePercentage() {


        long time = System.currentTimeMillis();

        float timePercentage = (float) (time - startTime) / 10000;

        Log.i("timePercentage", "time:" + timePercentage);
        return timePercentage;
    }

    private void drawLine(Canvas canvas) {

        for (int i = 0; i < paths.size(); i++) {
            canvas.drawPath(paths.get(i), paints.get(i));
        }
    }

    /**
     * all points in each layer can generate a path , drawPath() can generate line;
     */
    private void initPath() {

        for (int i = 0; i < pointLists.size(); i++) {
            Path path = new Path();
            paths.add(path);
            for (int j = 0; j < pointLists.get(i).size(); j++) {

                Point point = pointLists.get(i).get(j);
                if (j == 0) {
                    path.moveTo(point.x, point.y);
                } else {
                    path.lineTo(point.x, point.y);
                }
            }
        }

        Log.i("path.size", "path.size" + paths.size());
    }

    private void changePoints(float timePercentage, Canvas canvas) {
        for (int i = 0; i < pointLists.get(i).size() - 2; i++) {

            Path path = paths.get(i + 1);
            path.reset();

            for (int j = 0; j < pointLists.get(i).size() - 1; j++) {

                Point point1 = pointLists.get(i).get(j);
                Point point2 = pointLists.get(i).get(j + 1);

                int x = (int) ((point2.x - point1.x) * timePercentage + point1.x);
                int y = (int) ((point2.y - point1.y) * timePercentage + point1.y);

                if (j == 0) {
                    path.moveTo(x,y);
                }else{
                    path.lineTo(x, y);
                }

                canvas.drawCircle(x, y, 8, paints.get(j));

            }
        }
    }


}
