package com.example.customview.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.example.customview.R;

/**
 * Created by to-explore-future on 2017/8/13
 */
public class YTestView extends View {

    private Context mContext;
    private String mText;
    private int mColor;
    private int mTextSize;
    private int mTextBg;
    private Paint mPaint;
    private Rect mBounds;
    private String[] texts = {"你摸了我一下", "别摸我", "你轻点", "手拿开"};


    public YTestView(Context context) {
        this(context, null);
    }

    public YTestView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        getViewAttr(attrs, defStyleAttr);
    }

    private void getViewAttr(AttributeSet attrs, int defStyleAttr) {

        // 布局文件中使用的属性 生成
        TypedArray typedArray = mContext.getTheme().obtainStyledAttributes(attrs, R.styleable.YTestView, defStyleAttr, 0);
        int lenght = typedArray.getIndexCount();
        for (int i = 0; i < lenght; i++) {      //每次解析一个
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.YTestView_viewText:
                    mText = typedArray.getString(attr);
                    Log.i("text", "mText:" + mText);
                    break;
                case R.styleable.YTestView_viewTextColor:
                    mColor = typedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.YTestView_viewTextSize:
                    mTextSize = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                            18,
                            getResources().getDisplayMetrics()));
                    break;
                case R.styleable.YTestView_viewTextBg:
                    mTextBg = typedArray.getColor(attr, Color.BLUE);
                    break;
            }
        }

        // 解析完成之后，typedArray 就没有用了，回收 释放资源
        typedArray.recycle();

        // 所有属性的值都通过布局文件拿到了，下一步开始绘制
        mPaint = new Paint();
        mPaint.setTextSize(mTextSize);
        //绘制文字 及相关的区域
        mBounds = new Rect();
        mPaint.getTextBounds(mText, 0, mText.length(), mBounds);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mText = texts[(int) (Math.random() * (texts.length - 1))];
                mBounds = new Rect();
                mPaint.getTextBounds(mText, 0, mText.length(), mBounds);
                postInvalidate();
            }
        });


    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(mTextBg);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        mPaint.setColor(mColor);
        Log.i("width+height", "width:" + getWidth() + "===heightn:" + getHeight());
        canvas.drawText(mText, getWidth() / 2 - mBounds.width() / 2, getHeight() / 2 - mBounds.height() / 2, mPaint);
    }
}
