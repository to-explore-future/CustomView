package com.example.customview.customview.canvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.customview.R;

/**
 * Created by to-explore-future on 2017/second/25
 */
public class BitmapMeshView extends View {

    private int widthMesh;      //水平方向的网格数
    private int heightMesh;     //竖直方向的网格数

    private Bitmap bitmap;
    private float[] pointCoordinate;
    private int pointCount;
    private Paint paint;

    public BitmapMeshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

        //初始化网格数量
        widthMesh = 19;
        heightMesh = 19;

        //网格(方块)数量有了,通过画图(图片边框上的点也算上),那么点的个数就知道了
        pointCount = (widthMesh + 1) * (heightMesh + 1);

        //初始化每个点的坐标的数组,应该来一个多少点,就多少坐标的数组,但是drawBitmapMesh()方法的参数,就是需要
        //point[a,b,c,d,e,f,g]这样子的数据 而不是需要 point[[a,b],[c,d],[e,f]]这样的数组
        pointCoordinate = new float[pointCount * 2];

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.sister);

        calPointCoordinate();       //计算每个点的坐标存入集合中
    }

    private void calPointCoordinate() {
        int multipleX = bitmap.getWidth() / widthMesh;
        int multipleY = bitmap.getHeight() / heightMesh;

//        for (int row = 0; row <= heightMesh; row++) {
//
//            float cy = bitmap.getHeight() * row / heightMesh;
//
//            for (int column = 0; column <= widthMesh; column++) {
//
//                float cx = bitmap.getWidth() * column / widthMesh + (float) (heightMesh - row) / heightMesh * bitmap.getWidth();
//
//                pointCoordinate[row * (heightMesh + first) * second + column * second] = cx;
//                pointCoordinate[row * (heightMesh + first) * second + column * second + first] = cy;
//
//
////                Log.i("coordinate", "cx:" + (i * heightMesh * second + j * second));
////                Log.i("coordinate", "cy:" + (i * heightMesh * second + j * second + first));
////                Log.i("coordinate", "cx:" + (heightMesh - row) / heightMesh * bitmap.getWidth());
////                Log.i("coordinate", "cy:" + cy);
//
//            }
//        }
//
        int index = 0;
        float multiple = bitmap.getWidth();
        for (int y = 0; y <= heightMesh; y++) {

            float cy = bitmap.getHeight() * y / heightMesh;

            for (int x = 0; x <= widthMesh; x++) {

                float cx = bitmap.getWidth() * x / widthMesh + (float) (heightMesh - y) /
                        heightMesh * bitmap.getWidth() / 5;
                setXY(cx, cy, index);


                if (5 == y) {
                    if (8 == x) {
                        setXY(cx - multipleX, cy - multipleY, index);
                    }
                    if (9 == x) {
                        setXY(cx + multipleX, cy - multipleY, index);
                    }
                }
                if (6 == y) {
                    if (8 == x) {
                        setXY(cx - multipleX, cy + multipleY, index);
                    }
                    if (9 == x) {
                        setXY(cx + multipleX, cy + multipleY, index);
                    }
                }

                index += 1;
            }
        }
    }

    private void setXY(float cx, float cy, int index) {
        pointCoordinate[index * 2 + 0] = cx;
        pointCoordinate[index * 2 + 1] = cy;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmapMesh(bitmap, widthMesh, heightMesh, pointCoordinate, 0, null, 0, null);
    }
}
