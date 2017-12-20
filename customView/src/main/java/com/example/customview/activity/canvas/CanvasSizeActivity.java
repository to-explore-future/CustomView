package com.example.customview.activity.canvas;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.customview.R;

public class CanvasSizeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas_size);


        ImageView imageView = (ImageView) findViewById(R.id.iv_canvas_size);
        Bitmap bitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888); //生成了一个空的bitmap,里面没有数值
        Canvas canvas = new Canvas(bitmap);                                     //把这个空的bitmap放到画布上,这样就限定了画布的大小
        canvas.drawColor(Color.RED);                                            //给画布配置颜色,其实画布上面放的是bitmap,所以其实也是给bitmap上一个颜色

        imageView.setImageBitmap(bitmap);

    }


}
