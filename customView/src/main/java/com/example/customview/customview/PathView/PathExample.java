package com.example.customview.customview.PathView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.example.customview.R;
import com.example.customview.utils.GetScreenWH;

/**
 * Created by to-explore-future on 2017/first/24
 */
public class PathExample extends View {

    private static final String TAG = "PathExample";
    private Paint mPaint;
    private Path mPath;
    private int screenW;
    private int screenH;
    private Bitmap bitmap;
    private Paint fillPaint;
    private Path path;
    private Path path1;
    private Path path2;
    private Path path3;

    public PathExample(Context context) {
        this(context, null);
    }

    public PathExample(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.star);

        DisplayMetrics outMetrics = GetScreenWH.getScreenWH(getContext());
        screenW = outMetrics.widthPixels;
        screenH = outMetrics.heightPixels;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(Color.parseColor("#B733F3"));
        //这个要设置,不设置描边,宽度也不设置,其实程序是运行了,但是你却看不出效果,
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);

        mPath = new Path();
        //LineTo() 是以起点为起点链接一条线
        //如果没有设置起点 那么默认是从(0,0)开始
        mPath.lineTo(300, 300);
        //起点
        mPath.moveTo(100, 100);
        mPath.lineTo(400, 200);
        //从绘制的终点为基础在移动对应的距离得到新的起点
        mPath.rMoveTo(200, 400);//(400 + 200,200+400)这时对应的起点是 (600,600)
        mPath.lineTo(800, 800);
        //rLineTo() 是以终点为起点加上 对应的点 得到一个新的点,以这个新的点为终点连接一条线
        mPath.rLineTo(200, 300);//终点是(800+200,800+300) = (1000,1100)

        //绘制bezier曲线,没有指定起点默认从 (0,0) 开始
        //如果有终点,默认终点就是起点
        mPath.moveTo(100, 100);
        mPath.quadTo(200, 300, 600, 500);

        //指定起点绘制bezier曲线,
        mPath.moveTo(200, 800);
        mPath.quadTo(300, 600, 400, 1000);

        //还可以根据三个点画bezier曲线
        mPath.moveTo(200, 1500);
        mPath.cubicTo(300, 1400, 400, 1500, 500, 1600);

        //绘制弧度
        RectF rect = new RectF(300, 800, 800, 1300);
        mPath.addRect(rect, Path.Direction.CW);
        mPath.addArc(rect, 0, 360);
        RectF rectF = new RectF(200, 500, 800, 800);
        mPath.addRect(rectF, Path.Direction.CW);
        mPath.addOval(rectF, Path.Direction.CW);

        mPath.addCircle(800, 300, 200, Path.Direction.CW);
        RectF rectF1 = new RectF(300, 50, 900, 350);

        mPath.addRoundRect(rectF1, 155, 15, Path.Direction.CW);
        mPath.addRoundRect(rectF1, 155, 45, Path.Direction.CW);
        mPath.addRoundRect(rectF1, 155, 75, Path.Direction.CW);
        mPath.addRoundRect(rectF1, 155, 100, Path.Direction.CW);
        mPath.addRoundRect(rectF1, 155, 155, Path.Direction.CW);
        //对path内的所有的数据都进行相加
//        mPath.offset(300,0);
//清空path里面的数据非结构性数据,比如一条线:保留两个端点,其余的去除,
//        mPath.rewind();

        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        fillPaint.setColor(Color.RED);

        path = new Path();
        RectF rectF2 = new RectF(400, 1500, 600, 1700);
        path.addRect(rectF2, Path.Direction.CW);
        path.addCircle(600, 1700, 100, Path.Direction.CW);
        path.setFillType(Path.FillType.EVEN_ODD);

        path1 = new Path();
        RectF rectF3 = new RectF(50, 1500, 250, 1700);
        path1.addRect(rectF3, Path.Direction.CW);
        path1.addCircle(250, 1700, 100, Path.Direction.CW);
        path1.setFillType(Path.FillType.INVERSE_EVEN_ODD);

        path2 = new Path();
        RectF rectF4 = new RectF(750, 1500, 950, 1700);
        path2.addRect(rectF4, Path.Direction.CW);
        path2.addCircle(950, 1700, 100, Path.Direction.CW);
        path2.setFillType(Path.FillType.INVERSE_WINDING);

        path3 = new Path();
        RectF rectF5 = new RectF(50, 1900, 250, 2100);
        path3.addRect(rectF5, Path.Direction.CW);
        path3.addCircle(250, 2100, 100, Path.Direction.CW);
        path3.setFillType(Path.FillType.WINDING);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(screenW, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(screenH * 2, MeasureSpec.EXACTLY);
        Log.i(TAG, "screenW:" + screenW);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.parseColor("#66B7E0F7"));
        canvas.drawBitmap(bitmap, 0, 0, mPaint);


        canvas.drawPath(mPath, mPaint);
        canvas.drawPath(path, fillPaint);
        canvas.drawPath(path1, fillPaint);
        canvas.drawPath(path2, fillPaint);
        canvas.drawPath(path3, fillPaint);

    }
}
