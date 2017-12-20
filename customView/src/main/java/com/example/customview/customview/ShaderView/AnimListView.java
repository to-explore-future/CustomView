package com.example.customview.customview.ShaderView;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by to-explore-future on 2017/second/24
 */
public class AnimListView extends ListView {

    private final Matrix matrix;
    private final Camera camera;

    public AnimListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        matrix = new Matrix();
        camera = new Camera();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        camera.save();
        camera.rotate(10, -10, 10);        //rotate(x,y,z) 分别沿着x,y,z轴旋转
        camera.getMatrix(matrix);

        matrix.preTranslate(-getWidth() / 2, -getHeight() / 2);
        matrix.postTranslate(getWidth() / 2, getHeight() / 2);

//        matrix.preTranslate(-2000, -2000);
//        matrix.postTranslate(2000, 2000);

        canvas.concat(matrix);      //
        super.onDraw(canvas);

        camera.restore();

    }
}
