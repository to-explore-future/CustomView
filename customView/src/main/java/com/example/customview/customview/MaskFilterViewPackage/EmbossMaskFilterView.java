package com.example.customview.customview.MaskFilterViewPackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;

import com.example.customview.activity.colorFilter.EmbossMaskFilterActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by to-explore-future on 2017/second/14
 */
public class EmbossMaskFilterView extends View {

    private Paint paint;
    private Bitmap chocolate;
    private RectF rectF;
    private ArrayList<RectF> rectfs;
    private float lightSourceY = 1;
    private float lightSourceX = (float) Math.tan(89 * Math.PI / 180);
    private float lightSourceZ = 1;

    private Handler handler = new MyHandler(new WeakReference<EmbossMaskFilterView>(this));

    private static class MyHandler extends Handler {

        private WeakReference<EmbossMaskFilterView> mView;
        private EmbossMaskFilterView embossMaskFilterView;
        private EmbossMaskFilterActivity.LightStateCallBack lightState;

        public MyHandler(WeakReference<EmbossMaskFilterView> mView) {
            this.mView = mView;
            embossMaskFilterView = mView.get();
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.obj != null) {
                lightState = embossMaskFilterView.getLightState();
                lightState.changeState(msg.obj.toString());
            } else {
                embossMaskFilterView.invalidate();
            }
        }
    }

    private float[] direction;
    private EmbossMaskFilterActivity.LightStateCallBack lightState;


    public EmbossMaskFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        initRect();
        initPaint();
    }

    private void initRect() {

        rectfs = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                rectF = new RectF(200 * j, 200 * (i + 1), 200 * j + 200, 200 * (i + 1) + 200);
                rectfs.add(rectF);
            }
        }

        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //光源上升,y变大画坐标图,原点和x,y坐标点的连线就是光源的方向
                //刚开始的时候x无穷大,随着tan角的变小,x无限接近于0
                //这个时候就"艳阳高照"的时候

                Message message = new Message();
                message.obj = "光源上升";
                handler.sendMessage(message);

                for (int i = 0; i < 90 - 1; i++) {
                    SystemClock.sleep(50);
                    lightSourceX = (float) (Math.tan((90 - i) * Math.PI / 180) * lightSourceY);
                    handler.sendEmptyMessage(0);
                }

                //光源下降,x变小(或者y变大)画坐标图,原点和x,y坐标点的连线就是光源的方向
                //接着上面,刚开始的时候x无限小,接近0,
                //一直到光源再次下降到地平线,x又接近于无限大
                Message message1 = new Message();
                message1.obj = "光源下降";
                handler.sendMessage(message1);

                for (int i = 0; i < 90 - 1; i++) {
                    SystemClock.sleep(50);
                    lightSourceX = (float) (Math.tan((i + 1) * Math.PI / 180) * lightSourceY);
                    handler.sendEmptyMessage(0);
                }
            }
        }.start();
    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        //让刚开始的时候:照射光源在紧贴地平线的地方,x,y的tan角度接近于90度这个时候x无限大,y是1
        paint.setColor(Color.RED);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //这两句为什么 放到这里来,invalidate方法只是从新绘制了一下,并没有从新走构造方法,
        //所以有变化的参数的时候,调用invalidate,这些变化的参数要方法ondraw()里面来
        direction = new float[]{lightSourceX, lightSourceY, lightSourceZ};
        paint.setMaskFilter(new EmbossMaskFilter(direction, 0.1f, 10f, 20f));

        for (int i = 0; i < 20; i++) {
            canvas.drawRect(rectfs.get(i), paint);
        }
    }

    public void setLightState(EmbossMaskFilterActivity.LightStateCallBack lightState) {
        this.lightState = lightState;
    }

    public EmbossMaskFilterActivity.LightStateCallBack getLightState() {
        return this.lightState;
    }
}
