package com.example.customview.customview.colorFilterViewPackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.example.customview.R;
import com.example.customview.utils.DrawTextUtils;

/**
 * Created by to-explore-future on 2017/first/11 11:02
 */
public class CustomView1 extends View {

    private Paint paintCirclePoint;
    private Bitmap bitmap;
    private Bitmap bitmapStar;
    private Paint paintCircle1;
    private Paint paintStar;
    private boolean isClick = false;

    public int getRadius() {
        return radius;
    }

    private int radius = 200;


    private WindowManager windowManager;
    private int screenWidth;
    private int screenHeigth;
    private Paint paintCircle;

    public CustomView1(Context context) {
        this(context, null);
    }

    public CustomView1(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initData(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i("DrawCircle", "测量前:widthMeasureSpec:" + widthMeasureSpec + "--heightMeasureSpec:" +
                heightMeasureSpec);
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(bitmap.getWidth(), MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(3400, MeasureSpec.EXACTLY);
        Log.i("DrawCircle", "测量后:widthMeasureSpec:" + widthMeasureSpec + "--heightMeasureSpec:" +
                heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    private void initData(Context context) {
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeigth = displayMetrics.heightPixels;
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.color_filer_test);
        bitmapStar = BitmapFactory.decodeResource(getResources(), R.mipmap.star);
    }

    private void initPaint() {
        //Paint.ANTI_ALIAS_FLAG  抗锯齿
        paintCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        //只是填充
        paintCircle.setStyle(Paint.Style.FILL);
        paintCircle.setTextSize(40f);
        //设置边的宽度
        paintCircle.setStrokeWidth(10);
        //设置颜色
        float[] colorMatrix = new float[]{
                1, 0, 0, 0, 0,
                0, 1, 0, 0, 0,
                0, 0, 1, 0, 0,
                0, 0, 0, 1, 0,
        };
        ColorFilter colorMatrixColorFilter = new ColorMatrixColorFilter(colorMatrix);
        paintCircle.setColorFilter(colorMatrixColorFilter);
        paintCircle.setColor(Color.RED);

        paintCircle1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        //只是描边
        paintCircle1.setStyle(Paint.Style.FILL);
        //设置边的宽度
        paintCircle1.setStrokeWidth(10);
        //设置颜色
        float[] colorMatrix1 = new float[]{
                1, 0, 0, 0, 0,
                0, 1, 0, 0, 0,
                0, 0, 1, 0, 0,
                0, 0, 0, 1, 0,
        };
        ColorFilter colorFilter1 = new ColorMatrixColorFilter(colorMatrix1);
        //two parameters:the
        // first parameter int mul :parameters of the form is 0xFFFFFFFF ,每两位表示一个一个像素中的通道
        //表示顺序是ARGB,不论是第一个paremeter or the second parameter ,A doesn't work,
        // if the paremeter is 0xFFFFFFFF ,it means keep the picture it self,
        // if the parameter is 0xFFFFFF00 ,it means the B channel is zero , there is no blue in
        // the picture,
        //second paremeter int add:parameters of the form is 0xFFFFFFFF ,每两位代表一个像素中的一个颜色值
        //第二个参数表示在原来的基础上 进行颜色值的相加,0x00000000 表示颜色保持不变,数值越大表示颜色变得越深,如果
        //最后的值变成0xFFFFFFFF,那么整张图片就变成白色
        ColorFilter lightingColorFilter = new LightingColorFilter(0xFFFF0000, 0x00000000);
        paintCircle1.setColorFilter(lightingColorFilter);
        paintCircle1.setColor(Color.GREEN);

        paintCirclePoint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintCirclePoint.setColor(Color.RED);
        paintCirclePoint.setStyle(Paint.Style.FILL);
        paintCirclePoint.setStrokeWidth(5);

        paintStar = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintStar.setStyle(Paint.Style.FILL_AND_STROKE);
        this.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isClick) {

                    isClick = false;
                    //如果松手,还原图片原来的颜色
                    ColorFilter starLightingColorFilter = new LightingColorFilter(0xFFFFFFFF, 0x00000000);
                    paintStar.setColorFilter(starLightingColorFilter);
                } else {

                    isClick = true;


                    //如果被点击,设置图片的颜色为黄色
                    ColorFilter starLightingColorFilter = new LightingColorFilter(0xFFFFFFFF,
                            0x00FFFF00);
                    paintStar.setColorFilter(starLightingColorFilter);
                }
                invalidate();
            }
        });
    }

    //自定义view 就要绘制,绘制工具 画笔,画布
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(screenWidth / 2, 300, radius, paintCircle);

        canvas.drawCircle(screenWidth / 2, 900, radius, paintCircle1);
        canvas.drawCircle(screenWidth / 2, 900, 5, paintCirclePoint);

        canvas.drawText("colorFilter有三个子类,第一个是:colorMatrixColorFilter", 20f, 1180f, paintCircle);
        canvas.drawBitmap(bitmap, 20f, 1200f, paintCircle);
        canvas.drawBitmap(bitmap, 20f, 2000f, paintCircle1);

        canvas.drawLine(20f, 2805f, 1000f, 2810f, paintCircle);
        canvas.drawText("第二张图片之所以是发红,是因为所有的颜色由三原色组", 20f, 2850f, paintCircle);
        canvas.drawText("成如果将第一张图片中的所有的黄色抽掉,就会是第二张", 20f, 2900f, paintCircle);
        canvas.drawText("图片的效果", 20f, 2950f, paintCircle);
        canvas.drawBitmap(bitmapStar, 20f, 2970f, paintStar);
        DrawTextUtils.drawText(canvas,"这个星星使用colorFilter的第二个类:lightingColorFilter",20f,3300f,
                paintCircle);
    }

    public void setRadius(int radius) {
        this.radius = radius;
        invalidate();
    }


}