package rotateanim.example.com.androidrotateanim;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import java.util.Iterator;
import java.util.Vector;

/**
 * 渐变圆弧
 */
public class GradientArcView extends View {

    private static final int ROUND_COLOR_DEFAULT = 0x33ffffff;
    private static final int ROUND_WIDTH_DEFAULT = 2; //dp
    private static final int ROUND_PROGRESS_COLOR_DEFAULT = 0xffffffff;

    /** 闪烁进度条每次移动的角度 */
    private static final int FLICKER_PRORESS_STEP = 40;
    /** 顶部作为计数起点, 右边是0，左边是-180或者180，底部是90 */
    private static final int START_POINT_TOP = -90;

    /** 定义一支画笔 */
    private Paint mPaint;
    private Paint mGradientArcPaint;
    private SweepGradient mSweepGradient;
    private int[] mColors = {
            0x00ffffff, 0x40ffffff, 0x80ffffff, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, 0x80ffffff, 0x40ffffff, 0x00ffffff
    };
    private Matrix mMatrix = new Matrix();
    /** 圆环的颜色 */
    private int mRoundColor;
    /** 圆环的宽度 */
    private float mRoundWidth;
    /** 圆环进度的颜色 */
    private int mRoundProgressColor;

    /** 进度旋转方向，顺时针(CLOCKWISE)或者逆时针(COUNTERCLOCKWISE) */
    private int mRotateOrientation;
    public static final int COUNTERCLOCKWISE = 0;
    public static final int CLOCKWISE = 1;

    /** 闪烁（循环旋转）进度条的时间 */
    private int mFlickerProgressTime = 1000;
    private int mFlickerProgressTotal = 0;

    /** 闪烁进度条动画正在进行 */
    private boolean isFlickerProgressWorking = false;

    private Vector<IRoundNumProgressListener> mRoundNumProgressListeners;

    public interface IRoundNumProgressListener {
        void onFlickerProgressEnd();
    }

    public GradientArcView(Context context) {
        this(context, null);
    }

    public GradientArcView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientArcView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mPaint = new Paint();
        mGradientArcPaint = new Paint();
        //获取自定义属性和默认值
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundNumProgressView);
        mRoundColor = mTypedArray.getColor(R.styleable.RoundNumProgressView_roundColor, ROUND_COLOR_DEFAULT);
        mRoundProgressColor = mTypedArray.getColor(R.styleable.RoundNumProgressView_roundProgressColor, ROUND_PROGRESS_COLOR_DEFAULT);

        final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        mRoundWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, ROUND_WIDTH_DEFAULT, metrics);
        mRotateOrientation = CLOCKWISE;
        mTypedArray.recycle();
    }

    public void addRoundNumProgressListener(IRoundNumProgressListener roundNumProgressListener) {
        if (roundNumProgressListener == null)
            return;
        if (mRoundNumProgressListeners == null) {
            mRoundNumProgressListeners = new Vector<IRoundNumProgressListener>();
        }
        mRoundNumProgressListeners.add(roundNumProgressListener);
    }

    private void notifyAllRoundNumProressListeners() {
        if (mRoundNumProgressListeners == null)
            return;
        Iterator<IRoundNumProgressListener> iterator = mRoundNumProgressListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onFlickerProgressEnd();
        }
    }

    int centerX = 0;     // 获取圆心的x坐标
    int radius =0;       // 圆环的半径
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (centerX == 0) {
            centerX = getWidth() / 2;                         // 获取圆心的x坐标
        }
        if (radius == 0) {
            radius = (int) (centerX - mRoundWidth / 2);       // 圆环的半径
        }
        // 画最外层的大圆环
        drawOuterCircle(canvas, centerX, radius);
        // 画闪烁的进度条动画
        if (isFlickerProgressWorking) {
            drawFlickerArcProgress(canvas, centerX, radius);
        } else {
            mGradientArcPaint.setStrokeWidth(mRoundWidth);         // 设置圆环的宽度
            mGradientArcPaint.setColor(mRoundProgressColor);       // 设置进度的颜色
            mGradientArcPaint.setAntiAlias(true);
            mGradientArcPaint.setStyle(Paint.Style.STROKE);
            // 定义一个梯度渲染，由于梯度渲染是从三点钟方向开始，所以再让他逆时针旋转90°，从0点开始
            if (mSweepGradient == null)
                mSweepGradient = new SweepGradient(centerX, centerX, mColors, null);
            mMatrix.setRotate(START_POINT_TOP, centerX, centerX);
            mSweepGradient.setLocalMatrix(mMatrix);
            mGradientArcPaint.setShader(mSweepGradient);
            canvas.drawCircle(centerX, centerX, radius, mGradientArcPaint); // 画出圆环
        }
    }

    private void drawOuterCircle(final Canvas canvas, final int centerX, final int radius) {
        mPaint.setColor(mRoundColor);                        // 设置圆环的颜色
        mPaint.setStyle(Paint.Style.STROKE);                 // 设置空心
        mPaint.setStrokeWidth(mRoundWidth);                  // 设置圆环的宽度
        mPaint.setAntiAlias(true);                           // 消除锯齿
        canvas.drawCircle(centerX, centerX, radius, mPaint); // 画出圆环
    }

    private void drawFlickerArcProgress(final Canvas canvas, final int centerX, final int radius) {
        drawGradientCircle(canvas, centerX, radius);
        post(new Runnable() {
            @Override
            public void run() {
                mFlickerProgressTotal += FLICKER_PRORESS_STEP;
                postInvalidate();
            }
        });
    }

    private void drawGradientCircle(final Canvas canvas, final int centerX, final int radius) {
        mGradientArcPaint.setStrokeWidth(mRoundWidth);         // 设置圆环的宽度
        mGradientArcPaint.setColor(mRoundProgressColor);       // 设置进度的颜色
        mGradientArcPaint.setAntiAlias(true);
        mGradientArcPaint.setStyle(Paint.Style.STROKE);
        // 定义一个梯度渲染，由于梯度渲染是从三点钟方向开始，所以再让他逆时针旋转90°，从0点开始
        if (mSweepGradient == null) {
            mSweepGradient = new SweepGradient(centerX, centerX, mColors, null);
        }
        if (mRotateOrientation == COUNTERCLOCKWISE) {   // 计数起点在顶部，所以为-90
            mMatrix.setRotate(START_POINT_TOP - mFlickerProgressTotal, centerX, centerX);
        } else if (mRotateOrientation == CLOCKWISE) {
            mMatrix.setRotate(START_POINT_TOP + mFlickerProgressTotal, centerX, centerX);
        }
        mSweepGradient.setLocalMatrix(mMatrix);
        mGradientArcPaint.setShader(mSweepGradient);
        canvas.drawCircle(centerX, centerX, radius, mGradientArcPaint); // 画出圆环
    }

    /***
     * 启动闪烁动画，点击的时候调用会起到很好的提示作用
     */
    public void startFlickerArcProgress(final Runnable endFlagRunnable) {
        if (isFlickerProgressWorking)
            return;
        isFlickerProgressWorking = true;
        postInvalidate();
        postDelayed(new Runnable() {
            @Override
            public void run() {
                isFlickerProgressWorking = false;
                postInvalidate();
                if (endFlagRunnable != null) {
                    endFlagRunnable.run();
                }
                notifyAllRoundNumProressListeners();
            }
        }, mFlickerProgressTime);
    }
}
