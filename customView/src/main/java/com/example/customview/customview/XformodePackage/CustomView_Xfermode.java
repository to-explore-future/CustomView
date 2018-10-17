package com.example.customview.customview.XformodePackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.view.View;

import com.example.customview.R;

/**
 * Created by to-explore-future on 2017/first/19 13:11
 */
public class CustomView_Xfermode extends View {

    private Bitmap circle;
    private Bitmap rectangle;
    private Bitmap mBack_Rectangle;
    private Bitmap mRectangle;
    private Bitmap mCircle;
    private Paint mPaint_OutRectangle;
    private Paint mPaint;
    private int rectangle_colors[];
    private int circle_colors[];
    private PorterDuffXfermode porterDuffXfermode;
    private int[] background_rect;

    public CustomView_Xfermode(Context context, PorterDuffXfermode mode) {
        super(context);
        porterDuffXfermode = mode;

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        //外边的大的正方形,只是画一个边框
        mPaint_OutRectangle = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint_OutRectangle.setStyle(Paint.Style.STROKE);
        mPaint_OutRectangle.setColor(Color.RED);
        mPaint_OutRectangle.setStrokeWidth(5f);
        //里面的一个圆,填充
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(35f);
        circle = BitmapFactory.decodeResource(getResources(), R.mipmap.circle);
        rectangle = BitmapFactory.decodeResource(getResources(), R.mipmap.circle);
        intData();
    }


    private void intData() {
        initRectangle();
        initCircle();
        initBackgroundRect();
        mRectangle = Bitmap.createBitmap(rectangle_colors, 140, 140, Bitmap.Config.ARGB_8888);
        mCircle = Bitmap.createBitmap(circle_colors, 140, 140, Bitmap.Config.ARGB_8888);
        mBack_Rectangle = Bitmap.createBitmap(background_rect, 220, 220, Bitmap.Config.ARGB_8888);
    }

    /**
     * 生成黄色的圆形所需要的数组
     */
    private void initCircle() {
        circle_colors = new int[140 * 140];
        for (int i = 0; i < 140; i++) {
            for (int j = 0; j < 140; j++) {
                int m = Math.abs(i - 70);
                int n = Math.abs(j - 70);
                int z = m * m + n * n;
                if (z < 70 * 70) {
                    circle_colors[i * 140 + j] = Color.parseColor("#F3BC12");
                } else {
                    circle_colors[i * 140 + j] = Color.parseColor("#00000000");
                }
            }
        }
    }

    /**
     * 生成蓝色正方形所需要的数组
     */
    private void initRectangle() {
        rectangle_colors = new int[140 * 140];
        for (int i = 0; i < 140; i++) {
            for (int j = 0; j < 140; j++) {
                rectangle_colors[i * 140 + j] = Color.parseColor("#56B3DC");
            }
        }
    }

    /**
     * 生成一个灰白相间的正方形的背景图所需要的数组
     */
    private void initBackgroundRect() {
        background_rect = new int[220 * 220];
        int white = Color.parseColor("#FFFFFF");
        int gray = Color.parseColor("#CCCCCC");
        int m = -1;
        //切记for循环中千万不要做运算,把所有的运算拿出来,做成变量,直接在for循环中赋值,提高效率
        for (int row = 0; row < 11; row++) {
            for (int i = 0; i < 20; i++) {                  //每一行由20个像素
                for (int column = 0; column < 11; column++) {
                    for (int j = 0; j < 20; j++) {          //每一个列也有20个像素
                        if (row % 2 > 0) {                  //如果行数是个奇数,首先显示白色方框
                            if (column % 2 > 0) {           //在行数为奇数的情况下,如果列数为奇数,就真的该显示白色方框了
                                background_rect[++m] = white;
                            } else {                        //                      列数为偶数,显示灰色
                                background_rect[++m] = gray;
                            }
                        } else {                            //剩下行,首先显示灰色方框
                            if (column % 2 > 0) {           //在行数为偶数的情况下,如果列数为奇数,就真的该显示白色方框了
                                background_rect[++m] = gray;
                            } else {                        //                      列数为偶数,显示灰色
                                background_rect[++m] = white;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(220, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(220, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //draw方法会调用onDraw方法,但是android建议 尽量不要重写 draw方法,
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(mBack_Rectangle, 0f, 0f, mPaint);
        //绘制一个rectangle边框,
        canvas.drawRect(0f, 0f, 220f, 220f, mPaint_OutRectangle);

        int sc = canvas.saveLayer(0f, 0f, 220f, 220f, mPaint_OutRectangle, Canvas.ALL_SAVE_FLAG);

        canvas.drawBitmap(mCircle, 5f, 5f, mPaint);
        canvas.drawText("Dst", 50f, 60f, mPaint);

        mPaint.setXfermode(porterDuffXfermode);

        /**
         * 设置 Xfermdoe 之后，到底发生了什么呢，
         * 就是说 上面有一个 savelayer 的动作，保存之前绘制的图形和后面的代码没有关系，这个就好像是 你在一个本子上画画
         * 画完一页之后呢 把这一页翻过去 saveLayer ，然后开始新的一页
         * 这个 setXfermode 在这个新的一页中起作用，setXfermode 之前我 draw 了一个 circle ，设置 Xfermode 之后呢，我又要 draw 一个正方形
         * 重点：这个时候 并不是直接把我要绘制的正方形直接绘制到屏幕上，而是-先要看看我设置 xfermode 之后的图形在画布中的区域，确定这个区域之后呢
         * 看看这个这个区域在内存中已经存在的颜色，
         * 现在的状态 ： 我将要绘制的区域已经存在的颜色 我知道，我将要在这个区域绘制的颜色我也知道了 一份区域有两份颜色，一份：已经存在的  另一份：我将要绘制的
         * 这时候就要使用 xfermode 了，xfermode 就是一种颜色的运算法则，这个区域中每个像素已经存在的颜色和将要绘制的颜色怎么运算
         * 计算机这时候就根据这个运算法则，把这个区域的已经存在的颜色和将要绘制的颜色 进行运算，运算完成之后得到一份新的颜色，
         * 最后把这份新的颜色 绘制到这个区域
         * 以上就是我对 xfermode 的最深刻的理解
         */
        canvas.drawBitmap(mRectangle, 75f, 75f, mPaint);
        canvas.drawText("Src", 130f, 160f, mPaint);
        /**
         * 这个是移除Xfermode模式,这里为什么要移除这个模式呢?如果不移除会怎么样呢?
         * 其实这里移除与不移除并不会产生什么效果,因为到这里位置该绘制都已经绘制到内存中了
         * (这个时候只是在内存中修改了是数据,还没真正展示到屏幕上,view中有一个draw方法,draw方法调用了onDraw,后续
         * 这些数据一定是传到底层的c代码中,真正是由驱动来控制屏幕上每个像素怎么展示),这个代码也是我从网上超过来的,
         * 但是我抄的作者的这句代码的意思有可能是考虑到mPaint这个画笔,有可能还要画别的东西,所以是处于良好的编程习惯,
         * 就随后把这个画笔的Xfermode模式去掉了!!
         */
        mPaint.setXfermode(null);


        canvas.restoreToCount(sc);

    }
}
