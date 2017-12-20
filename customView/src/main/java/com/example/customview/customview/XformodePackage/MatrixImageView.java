package com.example.customview.customview.XformodePackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.example.customview.R;
import com.example.customview.utils.GetScreenWH;

/**
 * Created by to-explore-future on 2017/second/23
 */
public class MatrixImageView extends ImageView {

    private static final String TAG = "MatrixImageView";
    public int screenHeight;
    public int screenWidth;

    private Context context;
    private Matrix currentMatrix;
    private Matrix saveMatrix;
    private PointF start;

    private PointF mid;
    private static final int MODE_NONE = 602;
    private static final int MODE_ZOOM = 262;
    private static final int MODE_DRAG = 256;
    private int mode;

    private Bitmap mBitmap;
    private float twoPointSpace;
    private PointF midPoint;
    private float scale = 1;
    private float degrees;

    public MatrixImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {

        getScreenWH();

        //初始化Matrix对象
        currentMatrix = new Matrix();
        saveMatrix = new Matrix();

        //初始化坐标点
        start = new PointF();
        mid = new PointF();

        //初始化模式
        mode = MODE_NONE;

        //设置图片资源
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.beautiful);
        //来一个宽度适应的
        mBitmap = Bitmap.createScaledBitmap(mBitmap, screenWidth,
                screenWidth * mBitmap.getHeight() / mBitmap.getWidth(), true);

        setImageBitmap(mBitmap);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:               //单点落下
                saveMatrix.set(currentMatrix);          //如果这里没有这行代码,在ACTION_MOVE MODE_DRAG的时候,
                // 每次currentMatrix都会设置一个全新的saveMatrix(相对于 0,0 ),每次图片都会回到原点再移动,
                mode = MODE_DRAG;                       //落下之后的操作只能是移动,(否则一个点还能干什么)
                start.set(event.getX(), event.getY());  //随时获取新的手指的坐标
                break;

            case MotionEvent.ACTION_POINTER_DOWN:       //当第二个点落到屏幕
                saveMatrix.set(currentMatrix);                                     //这个时候就可以得到两个点的距离,角度,中点

                twoPointSpace = calSpacing(event);      //得到两个点之间的距离
                midPoint = calMidPoint(event);

                degrees = calDegrees(event);
                mode = MODE_ZOOM;                       //两个手指的时候就是:旋转和缩放

                break;

            case MotionEvent.ACTION_POINTER_UP:         //当第二个手指离开屏幕
                mode = MODE_DRAG;
                start.set(event.getX(), event.getY());

            case MotionEvent.ACTION_UP:
                mode = MODE_NONE;

            case MotionEvent.ACTION_MOVE:               //触摸点移动时

                if (mode == MODE_DRAG) {

                    currentMatrix.set(saveMatrix);      //这句话的作用:每次都把matrix重置为一个新的,这样每次的preTranslate()或者postTranslate()就不会累加
                    float dx = event.getX() - start.x;
                    float dy = event.getY() - start.y;
                    Log.i("star", "dx: " + dx + "---dy: " + dy);
                    //像这种多次调用,如果使用的是preTranslate()或者postTranslate()就导致dx,dy累加
                    //但是setTranslate()就没有问题,因为这个setTranslate()每次都会清空前面的数据
                    //currentMatrix.setTranslate(dx, dy);
                    currentMatrix.postTranslate(dx, dy);
                    /**
                     * first.currentMatrix.set(saveMatrix);
                     * second,currentMatrix.preTranslate(dx, dy);
                     *
                     * 3.currentMatrix.set(saveMatrix);
                     * 4,currentMatrix.postTranslate(dx, dy);
                     * Maxtrix 里面的所有的方法,都有一个先后执行的顺序,就好像 pre , post , set 有顺序一样,
                     * 代码1,2和代码3,4的执行效果是不一样的 ,first,2是先移动后set , 而3,4 是先set后移动
                     * first.2导致图片移动异常(过快或者过慢)  而3,4正常移动
                     */
                    Log.i("scale", "scale:" + scale);
                    /**
                     * 这里总结一下:这里面的调用setTranslate()和每次set新的matrix,
                     * 然后在调用preTranslate()或者postTranslate()是一样的效果
                     * 这里为什么不用setTranslate,因为set会清除上次的信息,每次手指离开再次拖拽图片的时候,图片会回到原点
                     */
                } else if (mode == MODE_ZOOM && event.getPointerCount() == 2) {

                    currentMatrix.set(saveMatrix);

                    float newTwoPointSpace = calSpacing(event);
                    scale = newTwoPointSpace / twoPointSpace;
                    currentMatrix.postScale(scale, scale, midPoint.x, midPoint.y);

                    float degrees = calDegrees(event) - this.degrees;
                    currentMatrix.postRotate(degrees, midPoint.x,midPoint.y);   //旋转的话需要 角度和旋转中心

                }

                break;

        }

        setImageMatrix(currentMatrix);
        return true;
    }

    private float calDegrees(MotionEvent event) {
        float dx = event.getX(0) - event.getX(1);
        float dy = event.getY(0) - event.getY(1);
        double radian = Math.atan2(dy, dx);
        float degrees = (float) Math.toDegrees(radian);
        return degrees;

    }

    private PointF calMidPoint(MotionEvent event) {
        float dx = event.getX(0) + event.getX(1);
        float dy = event.getY(0) + event.getY(1);
        return new PointF(dx / 2, dy / 2);
    }

    private float calSpacing(MotionEvent event) {
        float dx = event.getX(0) - event.getX(1);
        float dy = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    private void getScreenWH() {
        DisplayMetrics screenWH = GetScreenWH.getScreenWH(getContext());
        screenHeight = screenWH.heightPixels;
        screenWidth = screenWH.widthPixels;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(screenWidth, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(screenHeight * 2, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


}
