package com.example.customview.customview.XformodePackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.example.customview.R;
import com.example.customview.utils.GetScreenWH;

/**
 * Created by to-explore-future on 2017/second/23
 */
public class MatrixImageView1 extends ImageView {

    private static final int MODE_NONE = 0x00123;// 默认的触摸模式
    private static final int MODE_DRAG = 0x00321;// 拖拽模式
    private static final int MODE_ZOOM = 0x00132;// 缩放or旋转模式
    private static final String TAG = "MatrixImageView1";

    private int mode;// 当前的触摸模式

    private float preMove = 1F;// 上一次手指移动的距离
    private float saveRotate = 0F;// 保存了的角度值
    private float rotate = 0F;// 旋转的角度

    private float[] preEventCoor;// 上一次各触摸点的坐标集合

    private PointF start, mid;// 起点、中点对象
    private Matrix currentMatrix, savedMatrix;// 当前和保存了的Matrix对象
    private Context mContext;// Fuck……

    public MatrixImageView1(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

        // 初始化
        init();
    }

    /**
     * 初始化
     */

    private void init() {
        /*
         * 实例化对象
         */
        currentMatrix = new Matrix();
        savedMatrix = new Matrix();
        start = new PointF();
        mid = new PointF();

        // 模式初始化
        mode = MODE_NONE;

        /*
         * 设置图片资源
         */
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.beautiful);
        bitmap = Bitmap.createScaledBitmap(bitmap, GetScreenWH.getScreenWH(mContext).widthPixels,
                GetScreenWH.getScreenWH(mContext).heightPixels, true);
        setImageBitmap(bitmap);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:// 单点接触屏幕时
                savedMatrix.set(currentMatrix);
                start.set(event.getX(), event.getY());
                mode = MODE_DRAG;
                preEventCoor = null;
                break;

            case MotionEvent.ACTION_POINTER_DOWN:   // 第二个点接触屏幕时
                preMove = calSpacing(event);

                if (preMove > 10F) {                //两个手指距离小于10,这种情况就不考虑缩放了
                    savedMatrix.set(currentMatrix);
                    calMidPoint(mid, event);
                    mode = MODE_ZOOM;
                }
                                                    //下面的每次都计算角度
                preEventCoor = new float[4];
                preEventCoor[0] = event.getX(0);
                preEventCoor[1] = event.getX(1);
                preEventCoor[2] = event.getY(0);
                preEventCoor[3] = event.getY(1);
                saveRotate = calRotation(event);
                Log.i(TAG, "saveRotate: "+saveRotate);
                break;
            case MotionEvent.ACTION_UP:             // 单点离开屏幕时

            case MotionEvent.ACTION_POINTER_UP:     // 第二个点离开屏幕时
                mode = MODE_NONE;
                preEventCoor = null;
                break;

            case MotionEvent.ACTION_MOVE:           // 触摸点移动时

                if (mode == MODE_DRAG) {            //单点触控拖拽平移

                    currentMatrix.set(savedMatrix);
                    float dx = event.getX() - start.x;
                    float dy = event.getY() - start.y;
                    currentMatrix.postTranslate(dx, dy);

                } else if (mode == MODE_ZOOM && event.getPointerCount() == 2) {//两点触控拖放旋转

                    float currentMove = calSpacing(event);
                    currentMatrix.set(savedMatrix);

                    if (currentMove > 10F) {        //指尖移动距离大于10F缩放
                        float scale = currentMove / preMove;
                        currentMatrix.postScale(scale, scale, mid.x, mid.y);
                    }

                    if (preEventCoor != null) {     //保持两点时旋转
                        rotate = calRotation(event);
                        Log.i("rotatea", "rotate: "+rotate);
                        float r = rotate - saveRotate;
                        currentMatrix.postRotate(r, mid.x,mid.y);
                    }
                }

                break;
        }

        setImageMatrix(currentMatrix);
        return true;
    }

    /**
     * 计算两个触摸点间的距离
     */
    private float calSpacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * 计算两个触摸点的中点坐标
     */
    private void calMidPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    /**
     * 计算旋转角度:得出第二个点相对第一个点的角度,可以把这个当做工具类
     */
    private float calRotation(MotionEvent event) {
        double deltaX = (event.getX(0) - event.getX(1));
        double deltaY = (event.getY(0) - event.getY(1));

        double radius = Math.atan2(deltaY, deltaX);     //取值范围 -π -- π
        double r = Math.atan2(1, Math.sqrt(3));
        float ra = (float) Math.toDegrees(r);
        Log.i("radius", "r: " + r+"转变成角度:"+ra);

        return (float) Math.toDegrees(radius);
    }
}
