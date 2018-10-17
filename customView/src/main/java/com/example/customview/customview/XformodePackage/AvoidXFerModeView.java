package com.example.customview.customview.XformodePackage;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 系统 avoidXfermode 已经弃用了 new 不出来
 */
public class AvoidXFerModeView extends View {

    private Context mContext;
    private Paint mPaint;

    public AvoidXFerModeView(Context context) {
        this(context,null);
    }

    public AvoidXFerModeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    }

}
