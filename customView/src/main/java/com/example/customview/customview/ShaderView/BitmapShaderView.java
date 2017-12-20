package com.example.customview.customview.ShaderView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.customview.R;
import com.example.customview.utils.GetScreenWH;

/**
 * Created by to-explore-future on 2017/second/20
 */
public class BitmapShaderView extends View {
    private TextView mTileState;
    private Paint paint;
    private int screenHeight;
    private int screenWidth;
    private Rect rect;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            invalidate();
        }
    };
    private Bitmap bitmap;
    private Shader.TileMode[] tiles;
    private String[] tileModeStateNames;
    private int x = 3;
    private int y = 3;
    private int z = 1000;
    private int i;
    private int j;
    public boolean loopState = true;


    public BitmapShaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setTextView(TextView tileState) {
        this.mTileState = tileState;
        invalidate();
    }

    private void init() {

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.colorfiltertestexemple);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

        tiles = new BitmapShader.TileMode[]{
                Shader.TileMode.REPEAT, Shader.TileMode.MIRROR, Shader.TileMode.CLAMP
        };
        tileModeStateNames = new String[]{"重复", "镜像", "拉伸"};

        DisplayMetrics screenWH = GetScreenWH.getScreenWH(getContext());
        screenHeight = screenWH.heightPixels;
        screenWidth = screenWH.widthPixels;
        Log.i("wh", "screenHeight:" + screenHeight + "---screenWidth:" + screenWidth);
        //如果画一个正方形,如果bottom比top还要小,肯定不显示
        int left = screenWidth / 2 - 500;
        int top = screenHeight / 2 - 800;
        int right = screenWidth / 2 + 500;
        int bottom = screenHeight / 2 + 800;
        rect = new Rect(left, top, right, bottom);

        toLoopState();
    }

    private void toLoopState() {
        new Thread() {

            @Override
            public void run() {
                for (int l = 0; l < z; l++) {

                    for (i = 0; i < x; i++) {
                        for (j = i; j < y; j++) {

                            handler.sendEmptyMessage(0);
                            SystemClock.sleep(1000);
                        }
                    }
                }
            }
        }.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(screenWidth, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(screenHeight, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //两个方向的tileMode tileY优先于tileX执行 ,首先考虑Y,然后在考虑X
        //clamp 针对的是bitmap的最下面的一条线或者是最右边的一条线进行拉伸
        paint.setShader(new BitmapShader(bitmap, tiles[i], tiles[j]));

        if (mTileState != null) {
            mTileState.setText("x轴:" + tileModeStateNames[i] + "---y轴:" + tileModeStateNames[j]);
        }
        canvas.drawRect(rect, paint);
    }

    //first.移除handler就可以了,但是循环还在继续
    //second.让循环结束
    public void stopLoop() {
        loopState = false;
        //马上停下来
        z = 0;
        x = 0;
        y = 0;
    }

    public void toLoop() {
        loopState = true;
        //恢复初始状态
        z = 1000;
        x = 3;
        y = 3;
        toLoopState();
    }
}
